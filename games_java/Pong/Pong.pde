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

PImage imgPaddle = spriteArt(".rrrrrr.\nrrmmmmrr\n" + "rmrrrrmr\n".repeat(42) + "rrmmmmrr\n.rrrrrr.");

PImage imgWall = spriteArt("yw".repeat(32), 4);

Sprite ball = createSprite(imgBall);
Sprite paddleL = createSprite(imgPaddle);
Sprite paddleR = createSprite(imgPaddle);
Sprite wallT = createSprite(imgWall);
Sprite wallB = createSprite(imgWall);

int ballVelocityX;
int ballVelocityY;

void setup() {
  size(256, 192);
  // places a ball in center of the screen
  ball.x = width / 2;
  ball.y = height / 2;
  paddleL.x = 5;
  paddleL.y = height / 2;
  paddleR.x = 245;
  paddleR.y = height / 2;
  wallT.x = 0;
  wallT.y = 8;
  wallB.x = 0;
  wallB.y = height - 8 - wallB.h;

  ballVelocityX = 1;
  ballVelocityY = 1;
}

void draw() {
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

  // check if the ball hits the paddles
  if (ball.x < paddleL.x + paddleL.w && ball.y < paddleL.y + paddleL.h && ball.y + ball.h > paddleL.y) {
    ballVelocityX = -ballVelocityX;
  }
  if (ball.x + ball.w > paddleR.x && ball.y + ball.h > paddleR.y && ball.y < paddleR.y + paddleR.h) {
    ballVelocityX = -ballVelocityX;
  }

  // check if the ball hits the walls
  if (ball.y < wallT.y + wallT.h) {
    if (ballVelocityX > 0) {
      ballVelocityX += 0.2;
    }
    else {
      ballVelocityX -= 0.2;
    }
    ballVelocityY = -ballVelocityY + 0.2;
  }

  if (ball.y + ball.h > wallB.y) {
    if (ballVelocityX > 0) {
      ballVelocityX += 0.2;
    }
    else {
      ballVelocityX -= 0.2;
    }
    ballVelocityY = -ballVelocityY - 0.2;
  }
  
  // check if the ball goes off screen
  if (ball.x + ball.w < -5) {
    ballVelocityX = 1;
  }
  if (ball.x > width + 5) {
    ballVelocityX = -1;
  }
  if (ball.x + ball.w < -5 || ball.x > width + 5) {
    if (Math.random() > .5) {
      ballVelocityY = -1;
    } 
    else {
      ballVelocityY = 1;
    }
    ball.x = width / 2;
    ball.y = height / 2;
  }

  ball.x += ballVelocityX;
  ball.y += ballVelocityY;
  
  paddleL.y = mouseY;
  paddleR.y = mouseY;

  drawSprites();
}
