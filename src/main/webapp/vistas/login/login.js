document.addEventListener('DOMContentLoaded', () => {
const form = document.getElementById('loginForm');
const usuario = document.getElementById('usuario');
const contraseña = document.getElementById('contraseña');
const mensajeError = document.getElementById('mensajeError');

form.addEventListener('submit', (e) => {
if (usuario.value.trim() === '' || contraseña.value.trim() === '') {
e.preventDefault();
mensajeError.textContent = 'Completa ambos campos';
mensajeError.style.display = 'block';
mensajeError.classList.add('shake');
setTimeout(() => mensajeError.classList.remove('shake'), 500);
} else {
mensajeError.style.display = 'none';
}
});
});

// Partículas de fondo
const canvas = document.getElementById('particles');
const ctx = canvas.getContext('2d');
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

let particlesArray = [];

class Particle {
constructor(){
this.x = Math.random() * canvas.width;
this.y = Math.random() * canvas.height;
this.size = Math.random() * 2 + 1;
this.speedX = Math.random() * 1 - 0.5;
this.speedY = Math.random() * 1 - 0.5;
}
update(){
this.x += this.speedX;
this.y += this.speedY;
if(this.x < 0 || this.x > canvas.width) this.speedX *= -1;
if(this.y < 0 || this.y > canvas.height) this.speedY *= -1;
}
draw(){
ctx.fillStyle = '#00ffd5';
ctx.beginPath();
ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
ctx.fill();
}
}

function initParticles(){
particlesArray = [];
for(let i = 0; i < 100; i++){
particlesArray.push(new Particle());
}
}

function animateParticles(){
ctx.clearRect(0, 0, canvas.width, canvas.height);
for(let i = 0; i < particlesArray.length; i++){
particlesArray[i].update();
particlesArray[i].draw();
}
requestAnimationFrame(animateParticles);
}

initParticles();
animateParticles();