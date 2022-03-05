// screen resolution is 256x192

PImage imgBall = spriteArt("""
..bbbb
.brrrrb
bbrbbrbb
bbrrrrbb
bbrrbbbb
bbrbrbbb
.brbbrb
..bbbb""");

PImage imgPaddleVert = spriteArt(".rrrrrr.\nrrmmmmrr\n" + "rmrrrrmr\n".repeat(42) + "rrmmmmrr\n.rrrrrr.");

PImage imgPaddleHoriz = spriteArt("." + "r".repeat(44) + ".\n" + "rr" + "m".repeat(42) + "rr\n" + ("rm" + "r".repeat(42) + "mr\n").repeat(4) + "rr" + "m".repeat(42) + "rr\n" + "." + "r".repeat(44));

PImage imgWall = spriteArt("yw".repeat(32), 4);

Sprite[] balls = new Sprite[4];
Sprite paddleL = createSprite(imgPaddleVert);
Sprite paddleR = createSprite(imgPaddleVert);
Sprite paddleT = createSprite(imgPaddleHoriz);
Sprite paddleB = createSprite(imgPaddleHoriz);
Sprite wallT = createSprite(imgWall);
Sprite wallB = createSprite(imgWall);

boolean[] activeBalls = new boolean[4];

int score = 0;
int activeCounter = 0;
int servedCounter = 0;
double speed = 0.4;

SoundFile bounceSound;
SoundFile offScreenSound;

boolean loaded = false;

void preload() {
	bounceSound = loadSound(QuintOS.dir + "/sounds/retro_collect_pickup_item_15.wav");
	offScreenSound = loadSound(QuintOS.dir + "/sounds/retro_impact_hit_12.wav");
	bounceSound.setVolume(0.3);
	offScreenSound.setVolume(0.3);
}

void setup() {
	for (int i = 0; i < 4; i++) {
		balls[i] = createSprite(imgBall);
		Sprite ball = balls[i];
		ball.x = 20;
  	ball.y = -20;
		activeBalls[i] = false;
	}
  // size(256, 192);
  // places a ball in center of the screen
  

  paddleL.x = 5;
  paddleL.y = height / 2;
	paddleL.immovable = true;
  paddleR.x = 245;
  paddleR.y = height / 2;
	paddleR.immovable = true;

	paddleT.x = width/2 - paddleT.w/2;
	paddleT.y = 10;
	paddleT.depth = 2;
	paddleT.immovable = true;
	paddleB.x = width/2 - paddleB.w/2;
	paddleB.y = 174;
	paddleB.depth = 2;
	paddleB.immovable = true;

  wallT.x = 0;
  wallT.y = 8;
	wallT.depth = 0;
  wallB.x = 0;
  wallB.y = height - 8 - wallB.h;
	wallB.depth = 0;

	loaded = true;

	text("Score: " + score, 23, 5);

	countdown();
}

void ballBounce(Sprite ball) {
	bounceSound.play();
	if (ball.velocity.x < 0) {
		ball.velocity.x -= 0.1;
	}
	else {
		ball.velocity.x += 0.1;
	}

	if (ball.velocity.y < 0) {
		ball.velocity.y -= 0.1;
	}
	else {
		ball.velocity.y += 0.1;
	}
	score++;
	text("Score: " + score, 23, 5);
}

void countdown() {
	text("3", 11, 15);
	delay(1000);
	text(" ", 11, 15);
	text("2", 12, 15);
	delay(1000);
	text(" ", 12, 15);
	text("1", 12, 16);
	delay(1000);
	text(" ", 12, 16);
	serve();
}

void serve() {
	for (int i = 0; i < 4; i++) {
		Sprite ball = balls[i];
		ball.x = width/2;
		ball.y = height/2;

		double[] diagonals = new double[]{0.25, 0.75, 1.25, 1.75};
		double theta = diagonals[(int)(Math.random() * 4)];
		theta += Math.random() * 0.3 - 0.15;
		theta *= Math.PI;
		
		// (x,y) = (rcos(θ), rsin(θ))

		ball.velocity.x = speed * Math.cos(theta);
		ball.velocity.y = speed * Math.sin(theta);

		activeBalls[i] = true;
		servedCounter++;
		activeCounter++;
		delay(3000);
	}
}

void draw() {
	if (!loaded) {
		return;
	}
  background(colorPal('w'));
  fill(colorPal('b'));
  rect(0, 0, width, 8);
  rect(0, height-8, width, 8);
  fill(colorPal('w'));
  stroke(colorPal('r'));
  circle(width / 2, height / 2, 80);
  fill(colorPal('u'));
  stroke(colorPal('w'));
  circle(width / 2, height / 2, 25);
  stroke(colorPal('r'));
  line(width / 2, wallT.y + wallT.h, width / 2, wallB.y);
  stroke(colorPal('u'));
  line(width * 0.2 , wallT.y + wallT.h, width * 0.2, wallB.y);
  line(width *  0.8, wallT.y + wallT.h, width * 0.8, wallB.y);
  
	for (int i = 0; i < 4; i++) {
		if (activeBalls[i] == false) {
			continue;
		}
		Sprite ball = balls[i];
		// check if the ball goes off screen

		if (ball.x + ball.w < -5 || ball.x > width + 5 || ball.y + ball.h < 0 || ball.y > height) {
			offScreenSound.play();
			ball.x = 20;
			ball.y = -20;
			ball.velocity.x = 0;
			ball.velocity.y = 0;
			activeBalls[i] = false;
			activeCounter--;
		}

		if (servedCounter >= 2 && activeCounter < 2) {
			text("Game over!", 23, 15);
			erase();
			score = 0;
			activeCounter = 0;
			servedCounter = 0;
			for (int k = 0; k < 4; k++) {
				balls[k].x = 20;
  			balls[k].y = -20;
			}
			setup();
		}
		
		paddleL.y = mouseY;
		paddleR.y = mouseY;
		paddleT.x = mouseX;
		paddleB.x = mouseX;

		// check if the ball hits the paddles
		ball.bounce(paddleL, () -> {
			this.ballBounce(ball);
		});
		ball.bounce(paddleR, () -> {
			this.ballBounce(ball);
		});

		ball.bounce(paddleT, () -> {
			this.ballBounce(ball);
		});
		ball.bounce(paddleB, () -> {
			this.ballBounce(ball);
		});
		for (int j = i + 1; j < 4; j++) {
			if (activeBalls[j]) ball.bounce(balls[j]);
		}
	}

  drawSprites();
}
