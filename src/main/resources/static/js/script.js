class KillWordsGame {
  constructor() {
    this.words = [
      'code', 'html', 'css', 'js', 'web',
      'react', 'vue', 'node', 'git', 'api'
    ];
    this.currentWord = '';
    this.deathLinePosition = 1;
    this.position = 0;
    this.score = 0;
    this.level = 1;
    this.isPlaying = false;
    this.gameSpeed = 1;
    this.activeWords = new Set();
    this.maxActiveWords = 3;
    this.wordSpawnInterval = null;
    
    // Get context element first
    this.context = document.getElementById('context');
    
    // Then initialize elements that depend on context
    this.initElements();
    this.initEvents();
  }

  initElements() {
    // Initialize other DOM elements first
    this.startScreen = document.getElementById('start-screen');
    this.endScreen = document.getElementById('end-screen');
    this.startButton = document.getElementById('start');
    this.startOverButton = document.getElementById('start-over');
    this.levelInput = document.getElementById('level');
    this.levelAgainInput = document.getElementById('level-again');
    this.resultDiv = document.getElementById('result');
    this.baddiesContainer = document.getElementById('baddies');
    this.bloodshed = document.getElementById('bloodshed');
    this.bloodshedCtx = this.bloodshed.getContext('2d');
    
    // Create and append death line after context is initialized
    this.deathLine = document.createElement('div');
    this.deathLine.className = 'death-line';
    this.context.appendChild(this.deathLine);
    
    // Set canvas dimensions to match container
    window.addEventListener('resize', () => this.updateDeathLinePosition());
    this.resizeCanvas();
    window.addEventListener('resize', () => this.resizeCanvas());
  }

  resizeCanvas() {
    this.bloodshed.width = this.context.offsetWidth;
    this.bloodshed.height = this.context.offsetHeight;
  }


  createDeathLine() {
    this.updateDeathLinePosition();
  }

  updateDeathLinePosition() {
    const containerHeight = this.context.offsetHeight;
    const deathLineY = containerHeight * this.deathLinePosition;
    this.deathLine.style.top = `${deathLineY}px`;
  }

  initEvents() {
    this.startButton.addEventListener('click', () => {
      this.level = parseInt(this.levelInput.value) || 1;
      this.startGame();
    });

    this.startOverButton.addEventListener('click', () => {
      this.level = parseInt(this.levelAgainInput.value) || 1;
      this.startGame();
    });

    document.addEventListener('keydown', (e) => this.handleKeypress(e));
  }

  startGame() {
    this.isPlaying = true;
    this.score = 0;
    this.activeWords.clear();
    this.gameSpeed = Math.min(1 + (this.level * 0.1), 2.5);
    this.startScreen.classList.remove('active');
    this.endScreen.classList.remove('active');
    this.context.classList.add('active');
    this.baddiesContainer.innerHTML = '';
    this.clearBloodshed();
    
    // Start spawning words
    this.wordSpawnInterval = setInterval(() => {
      if (this.activeWords.size < this.maxActiveWords) {
        this.spawnNewWord();
      }
    }, 2000 / this.gameSpeed);
  }

  spawnNewWord() {
    const word = this.words[Math.floor(Math.random() * this.words.length)];
    const wordElement = this.createWordElement(word);
    this.activeWords.add({
      word,
      element: wordElement,
      position: 0
    });
  }

  clearBloodshed() {
    this.bloodshedCtx.clearRect(0, 0, this.bloodshed.width, this.bloodshed.height);
  }

  drawExplosion(x, y) {
    const color = 'rgba(241, 103, 69, 0.2)';
    this.bloodshedCtx.beginPath();
    this.bloodshedCtx.arc(x, y, 25, 0, Math.PI * 2, false);
    this.bloodshedCtx.closePath();
    this.bloodshedCtx.fillStyle = color;
    this.bloodshedCtx.fill();
  }

  createWordElement(word) {
    const wordEl = document.createElement('div');
    wordEl.className = 'baddie';
    wordEl.style.position = 'absolute';
    wordEl.style.left = Math.random() * (this.context.offsetWidth - 100) + 'px';
    wordEl.style.top = '0';
    
    word.split('').forEach((char, index) => {
      const span = document.createElement('span');
      span.textContent = char;
      span.className = 'char';
      span.dataset.index = index;
      wordEl.appendChild(span);
    });

    this.baddiesContainer.appendChild(wordEl);
    this.animateWord(wordEl);
    return wordEl;
  }

  animateWord(wordEl) {
    let position = 0;
    const animate = () => {
      if (!this.isPlaying) return;
      
      position += this.gameSpeed;
      wordEl.style.top = position + 'px';

       // Check if word has reached the death line instead of container bottom
       const deathLineY = this.context.offsetHeight * this.deathLinePosition;
       if (position > deathLineY - wordEl.offsetHeight) {
        this.gameOver();
      } else if (this.isPlaying) {
        requestAnimationFrame(animate);
      }
    };
    requestAnimationFrame(animate);
  }

  handleKeypress(event) {
    if (!this.isPlaying) return;

    const pressedKey = event.key.toLowerCase();
    
    // Check each active word
    this.activeWords.forEach(activeWord => {
      const nextChar = activeWord.word[activeWord.position];
      
      if (pressedKey === nextChar) {
        // Mark letter as hit
        const charElement = activeWord.element.querySelector(`[data-index="${activeWord.position}"]`);
        if (charElement) {
          charElement.className = 'char hit';
          const rect = charElement.getBoundingClientRect();
          const contextRect = this.context.getBoundingClientRect();
          this.drawExplosion(
            rect.left - contextRect.left + rect.width / 2,
            rect.top - contextRect.top + rect.height / 2
          );
        }

        activeWord.position++;
        this.score += 10;

        // Word completed
        if (activeWord.position === activeWord.word.length) {
          this.score += 50; // bonus
          activeWord.element.className = 'baddie killed';
          this.activeWords.delete(activeWord);
          setTimeout(() => {
            activeWord.element.remove();
          }, 500);
        }
      }
    });
  }

  gameOver() {
    this.isPlaying = false;
    clearInterval(this.wordSpawnInterval);
    this.context.classList.remove('active');
    this.endScreen.classList.add('active');
    
    this.resultDiv.innerHTML = `
      <ul>
        <li><span class="label">Level</span><span class="value">${this.level}</span></li>
        <li><span class="label">Score</span><span class="value">${this.score}</span></li>
      </ul>
    `;
  }
}

// Initialize game
const game = new KillWordsGame();

