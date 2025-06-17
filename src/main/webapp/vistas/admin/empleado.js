// Assume CONTEXT_PATH is defined globally in the JSP, e.g., <script>const CONTEXT_PATH = '${pageContext.request.contextPath}';</script>

document.addEventListener("DOMContentLoaded", () => {
    const btnAgregar = document.getElementById("btnAgregar");
    if (btnAgregar) {
        btnAgregar.addEventListener("click", () => {
            document.getElementById("formEmpleado").reset();
            document.getElementById("idUsuario").value = ""; // Clear hidden ID field for adding
            document.getElementById("contraseña").value = ""; // Clear password
            document.getElementById("confirmarContraseña").value = ""; // Clear confirm password
            document.getElementById("passwordHelp").textContent = ""; // Clear any previous password help text
            document.getElementById("formularioEmpleado").classList.remove("oculto");
        });
    }

    const formEmpleado = document.getElementById('formEmpleado');
    if (formEmpleado) {
        formEmpleado.addEventListener('submit', function(event) {
            event.preventDefault();

            const formData = new FormData(formEmpleado);
            const plainFormData = Object.fromEntries(formData.entries());
            const idUsuario = plainFormData.idUsuario; // This is from the hidden input

            // Client-side validation for password confirmation (optional, but good practice)
            if (plainFormData.contraseña !== plainFormData.confirmarContraseña) {
                alert("Las contraseñas no coinciden.");
                return;
            }

            // Remove confirmarContraseña before sending to server if it exists
            delete plainFormData.confirmarContraseña;


            const url = CONTEXT_PATH + '/EmpleadoServlet';

            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded', // Standard for URLSearchParams
                },
                body: new URLSearchParams(plainFormData)
            })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    if (idUsuario) { // Editing
                        actualizarFilaTabla(data.usuario);
                    } else { // Adding
                        agregarFilaTabla(data.usuario);
                    }
                    cerrarFormulario();
                    alert(data.message || "Operación exitosa.");
                } else {
                    alert(data.message || "Ocurrió un error.");
                }
            })
            .catch(error => {
                console.error('Error en la solicitud fetch:', error);
                alert('Error al conectar con el servidor.');
            });
        });
    }

    // Initial load of employees via AJAX if the table exists
    // This part is new and assumes you want to load data via AJAX on page load.
    // If data is already loaded by JSP, this can be removed or adapted.
    // For this subtask, we assume the table might be initially empty or server-rendered,
    // and JS will primarily handle dynamic updates.
    // If you want to load all data via AJAX upon page load:
    // fetchEmpleados();
});

// function fetchEmpleados() {
//     fetch(CONTEXT_PATH + '/EmpleadoServlet?accion=listar&format=json')
//         .then(response => response.json())
//         .then(data => {
//             const tbody = document.querySelector('#tablaEmpleados tbody');
//             tbody.innerHTML = ''; // Clear existing rows
//             data.forEach(usuario => agregarFilaTabla(usuario));
//         })
//         .catch(error => console.error('Error al cargar empleados:', error));
// }

function editar(id, nombre, rol) {
    document.getElementById("idUsuario").value = id;
    document.getElementById("nombreUsuario").value = nombre;
    document.getElementById("rol").value = rol;
    // Password fields are typically cleared or handled separately for edits
    document.getElementById("contraseña").value = "";
    document.getElementById("confirmarContraseña").value = "";
    document.getElementById("passwordHelp").textContent = "Dejar en blanco para no cambiar la contraseña.";
    document.getElementById("formularioEmpleado").classList.remove("oculto");
}

function eliminar(idUsuario) {
    if (confirm("¿Estás seguro de eliminar al empleado con ID " + idUsuario + "?")) {
        fetch(CONTEXT_PATH + '/EmpleadoServlet?accion=eliminar&idUsuario=' + idUsuario)
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    eliminarFilaTabla(idUsuario);
                    alert(data.message || "Empleado eliminado.");
                } else {
                    alert(data.message || "Error al eliminar el empleado.");
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error al conectar con el servidor para eliminar.');
            });
    }
}

function cerrarFormulario() {
    const form = document.getElementById("formEmpleado");
    if (form) form.reset();
    document.getElementById("idUsuario").value = ""; // Ensure ID is cleared
    document.getElementById("passwordHelp").textContent = "";
    const formularioDiv = document.getElementById("formularioEmpleado");
    if (formularioDiv) formularioDiv.classList.add("oculto");
}

function agregarFilaTabla(usuario) {
    const tbody = document.querySelector('#tablaEmpleados tbody');
    if (!tbody) {
        console.error("El tbody de la tabla #tablaEmpleados no fue encontrado.");
        return;
    }
    const newRow = tbody.insertRow();
    newRow.setAttribute('id', 'empleado-' + usuario.idUsuario);

    newRow.insertCell().textContent = usuario.idUsuario;
    newRow.insertCell().textContent = usuario.nombreUsuario;
    newRow.insertCell().textContent = usuario.rol;

    const accionesCell = newRow.insertCell();
    const editBtn = document.createElement('button');
    editBtn.textContent = 'Editar';
    editBtn.className = 'btn btn-primary btn-sm'; // Bootstrap classes
    editBtn.onclick = () => editar(usuario.idUsuario, usuario.nombreUsuario, usuario.rol);
    accionesCell.appendChild(editBtn);

    const deleteBtn = document.createElement('button');
    deleteBtn.textContent = 'Eliminar';
    deleteBtn.className = 'btn btn-danger btn-sm ms-1'; // Bootstrap classes
    deleteBtn.onclick = () => eliminar(usuario.idUsuario);
    accionesCell.appendChild(deleteBtn);
}

function actualizarFilaTabla(usuario) {
    const row = document.getElementById('empleado-' + usuario.idUsuario);
    if (row) {
        row.cells[1].textContent = usuario.nombreUsuario; // NombreUsuario
        row.cells[2].textContent = usuario.rol;         // Rol
        // Update onclick for the edit button if necessary (data might have changed)
        const editBtn = row.querySelector('.btn-primary'); // Assuming edit button has btn-primary
        if (editBtn) {
            editBtn.onclick = () => editar(usuario.idUsuario, usuario.nombreUsuario, usuario.rol);
        }
    } else {
        console.warn("No se encontró la fila para actualizar:", 'empleado-' + usuario.idUsuario);
        // Optionally, add as new if not found, though typically update implies existence
        // agregarFilaTabla(usuario);
    }
}

function eliminarFilaTabla(idUsuario) {
    const row = document.getElementById('empleado-' + idUsuario);
    if (row) {
        row.remove();
    } else {
        console.warn("No se encontró la fila para eliminar:", 'empleado-' + idUsuario);
    }
}