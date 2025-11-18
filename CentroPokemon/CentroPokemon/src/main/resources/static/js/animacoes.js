/**
 * @file        animacoes.js
 * @summary     Animações e interações leves do Centro Pokémon
 * @description Timer de oferta, efeitos nos botões, navegação ativa,
 *              partículas decorativas e transições de páginas.
 * @selectors   .timer-display, .quick-btn, nav a, .pagina, .pagina-content
 */

document.addEventListener('DOMContentLoaded', function() {
    // Timer functionality - Countdown display
    var timerEl = document.querySelector('.timer-display');
    if (timerEl) {
        var parts = timerEl.textContent.trim().split(':');
        var secs = (+parts[0])*3600 + (+parts[1])*60 + (+parts[2]);
        
        /** Atualiza o visor do timer de oferta a cada segundo */
        function tick() {
            if (secs > 0) secs--;
            var h = Math.floor(secs/3600);
            var m = Math.floor((secs%3600)/60);
            var s = secs%60;
            timerEl.textContent = String(h).padStart(2,'0')+':'+String(m).padStart(2,'0')+':'+String(s).padStart(2,'0');
        }
        setInterval(tick, 1000);
    }

    // Button click animations - Quick buttons
    document.querySelectorAll('.quick-btn').forEach(function(btn) {
        btn.addEventListener('click', function() {
            btn.style.transform = 'translateY(2px) scale(0.98)';
            setTimeout(function(){ btn.style.transform = ''; }, 150);
        });
    });

    // Navigation active state
    var navLinks = document.querySelectorAll('nav a');
    navLinks.forEach(function(link) {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            navLinks.forEach(function(l){ l.classList.remove('active'); });
            link.classList.add('active');
        });
    });

    // Particle animation system
    var content = document.querySelector('.pagina-content');
    if (content) {
        var particles = document.createElement('div');
        particles.className = 'particles';
        content.appendChild(particles);
        
        for (var i = 0; i < 20; i++) {
            var p = document.createElement('span');
            p.className = 'particle';
            p.style.left = Math.random()*100 + '%';
            p.style.top = Math.random()*100 + '%';
            p.style.animationDelay = (Math.random()*3)+'s';
            particles.appendChild(p);
        }
    }

    // Page transition animations
    var pages = document.querySelectorAll('.pagina');
    if (pages.length > 1) {
        // Simple page switching logic
        /**
         * Alterna página ativa pela id de âncora
         * @param {string} pageId
         */
        var showPage = function(pageId) {
            pages.forEach(function(page) {
                page.classList.remove('ativa');
            });
            document.getElementById(pageId).classList.add('ativa');
        };

        // Connect navigation to page switching
        navLinks.forEach(function(link) {
            link.addEventListener('click', function() {
                var targetPage = this.getAttribute('href').substring(1);
                if (targetPage) {
                    showPage(targetPage);
                }
            });
        });
    }
});