//// Kevin Schaefer
//// Project 3
//// CST 112 EVE

//// 5 balls, cue, 4 buttons ////
Ball a,b,c,d,e, cue;
Button aa, bb, cc, dd;

//// OTHER GLOBALS:  strings, pool table, etc ////
String news=   "Click any ball to reset it to right half of table.  ('r' resets all)";
String author=  "Kevin Schaefer";
String display= "Score:";

float left=120, right=520, top=165, bottom=315;        // Table boundaries
float middle=250;
boolean wall=true;
boolean ratclick=false;
float ratX = left;
float ratY= random(bottom, top);

int tableRed=150, tableGreen=250, tableBlue=150;      // Green pool table
int score=0,m=0,k=0;
int count= 0;


//// SETUP:  size, table, balls, buttons
void setup() {
    size ( 700, 500 );
    left = ((width/2)-200);
    top = ((height/2)-75); 
    right = ((width/2)+200);
    bottom = ((height/2)+75);
    middle = (width/2);
    
    a = new Ball();
    a.r= 255; a.name= "1";
    b = new Ball();
    b.g= 255; b.name= "2";
    c = new Ball();
    c.g= 255; c.b= 255; c.name= "3";
    d = new Ball();
    d.r= 255; d.g=255; d.name= "4";
    e = new Ball();
    e.r= 200; e.b=255; e.name= "5";
    cue = new Ball();
    cue.r= 255; cue.g= 255; cue.b= 255;
    
    aa = new Button();
    aa.name= "RESET"; aa.x=100; aa.y=50; aa.w=60; aa.h=20;
    bb = new Button();
    bb.name= "WALL"; bb.x=180; bb.y=50; bb.w=60; bb.h=20;
    cc = new Button();
    cc.name= "BIRD"; cc.x=260; cc.y=50; cc.w=60; cc.h=20;
    dd = new Button();
    dd.name= "RAT"; dd.x=340; dd.y=50; dd.w=60; dd.h=20;
    
    reset() ;
}

// Creates the reset/default game start
void reset() {
  score= 0;
  wall=true;
  ratX=left;
  ratclick=false;
  tableRed=150; tableGreen=250; tableBlue=150;
  
  // Set position for ball spawn
  a.x= random(middle+40, right); a.y= random(top, bottom);
  b.x= random(middle+40, right); b.y= random(top, bottom);
  c.x= random(middle+40, right); c.y= random(top,bottom);
  d.x= random(middle+40, right); d.y= random(top,bottom);
  e.x= random(middle+40, right); e.y= random(top,bottom);
  cue.x= (left+right)/3;         cue.y= (top+bottom)/2;
  
  // Set the speed for ball spawn
  a.dx=  random( -3,3 );   a.dy=  random( -3,3 );
  b.dx=  random( -3,3 );   b.dy=  random( -3,3 );
  c.dx=  random( -3,3 );   c.dy=  random( -3,3 );
  d.dx=  random( -3,3 );   d.dy=  random( -3,3 );
  e.dx=  random( -3,3 );   e.dy=  random( -3,3 );
  cue.dx= 0;               cue.dy= 0;
}

//// Draw scene and call functions
void draw() {
  background( 250,250,200 );
  rectMode( CORNERS );
  table( left, top, right, bottom );  
  balls();   
  grass();
  clouds();
  count += 1;
  if (key == 'm' && ratX >left ) { rat(); }
  if (ratclick = true && ratX > left) { rat(); }
  messages();
  buttons();
}

//// Different functions when different keys pressed
void keyPressed() {
  if (key == 'q') { exit();  }
  if (key == 'r') { reset(); }
  if (key == 'w') { wall=false; }  // Remove wall
  if (key == 'p') { tableRed= 250; tableGreen=150; tableBlue=235; } // Pink table
  
  /// 'm' starts rat animation
  if (key == 'm') { rat(); }  
  
  /// Number keys reset balls (reset reduces score)
  if (key == '1') { a.reset(); score -= 5; }
  if (key == '2') { b.reset(); score -= 5; }
  if (key == '3') { c.reset(); score -= 5; }
  if (key == '4') { d.reset(); score -= 5; }
  if (key == '5') { e.reset(); score -= 5; }
  if (key == 'c') { cue.x= (left+right)/3; cue.y= (top+bottom)/2; cue.dx=0; cue.dy=0; score -= 5; }
}

/// Resets balls when clicked (reset reduces score)
void mouseClicked() {
  if ( dist(a.x,a.y, mouseX,mouseY) < 18) { a.reset(); score -= 5; }
  if ( dist(b.x,b.y, mouseX,mouseY) < 18) { b.reset(); score -= 5; }
  if ( dist(c.x,c.y, mouseX,mouseY) < 18) { c.reset(); score -= 5; }
  if ( dist(d.x,d.y, mouseX,mouseY) < 18) { d.reset(); score -= 5; }
  if ( dist(e.x,e.y, mouseX,mouseY) < 18) { e.reset(); score -= 5; }
  if ( dist(cue.x,cue.y, mouseX,mouseY) < 18) { 
    cue.x= (left+right)/3; cue.y= (top+bottom)/2; cue.dx=0; cue.dy=0; }
    
  // Reset/Wall/Bird/Rat functions when different buttons are clicked  
  if ( mouseX > aa.x && mouseX < aa.x+aa.w &&
    mouseY > aa.y && mouseY < aa.y+aa.h ) {
      reset(); 
    }
  if ( mouseX > bb.x && mouseX < bb.x+bb.w &&
    mouseY > bb.y && mouseY < bb.y+bb.h ) {
      wall=false;
    }
  if ( mouseX > cc.x && mouseX < cc.x+cc.w &&
    mouseY > cc.y && mouseY < cc.y+cc.h ) {
      //bird();
    }
  if ( mouseX > dd.x && mouseX < dd.x+dd.w &&
    mouseY > dd.y && mouseY < dd.y+dd.h ) {
      rat();
    }    
}

//// Scene: Table w/ wall in middle
void table( float east, float north, float west, float south ) {
  fill( tableRed, tableGreen, tableBlue );    // pool table
  strokeWeight(20);
  stroke( 127, 0, 0 );      // Brown walls
  rect( east-20, north-20, west+20, south+20 );

  // Start with a WALL down the middle of the table
  if (wall==true) {
    float middle=  (east+west)/2;    
    stroke( 0, 127, 0 );
    line( middle,north+10, middle,south-10 );
  }
  stroke(0);
  strokeWeight(1);
}

// Draws and creates the rat
void rat() {
  if (ratX < right) {
    stroke(245,150,220);                          // tail
    strokeWeight(3);
    line(ratX-40, ratY, ratX,ratY); 
    stroke(0,0,0);
    strokeWeight(2);
    
    if (count/30 % 2 == 0) {
      line(ratX-20,ratY, ratX-20,ratY+15);  // back left
      line(ratX+10,ratY, ratX+10,ratY+15);  // front right
      line(ratX-15,ratY, ratX-15,ratY+15);  // back right
      line(ratX+5,ratY, ratX+5,ratY+15);    // front left
    } else {
      line(ratX-20,ratY, ratX-15,ratY+15);  // angle back left
      line(ratX+10,ratY, ratX+10,ratY+15);  // front right
      line(ratX-15,ratY, ratX-15,ratY+15);  // back right      
      line(ratX+5,ratY, ratX+10,ratY+15);   // angle front left
    }
    
    strokeWeight(1);
    fill(125,125,125);
    ellipse(ratX,ratY, 50,20);      // body   
    ellipse(ratX+2,ratY-11,17,17);  // ears    
    fill(0,0,0);
    ellipse(ratX+14,ratY-4,3,3);    // eyes
    ellipse(ratX+25,ratY,3,3);      // nose
    ratX += 2;
  } else { ratX = left;             // sets rat back at start location
  }
}

// Display messages
void messages() {
  fill(0);
  text( news, width/9, 30 );
  text( author, 10, height-5 );
  text( display, width-200, 30);
  text( score, width-140, 30);
}

// Draws the grass across bottom
void grass() {
  stroke(40,165,60);
  strokeWeight(2);
  int x = 0;
  float y = bottom + 30;
  while (y < height+10) {
    for (x=0; x<width+10; x += 7) {
      line(x,y, x+7, y-7);
    }
    y += 5; 
  strokeWeight(1);
  }
}

// Draws the clouds across the top
void clouds() {
  for (int i=0; i<width; i += 80) {
    //float r = random(0, top-25);
    int r = 130;
    noStroke();
    fill(230,230,230);
    ellipse(i+10,r-10, 30,30);
    ellipse(i-10,r-7, 20,20);
    ellipse(i,r, 70,20);
  }
} 

// Function to display the buttons
void buttons() {
  aa.show();
  bb.show();
  cc.show();
  dd.show();
}

// Ball collisions/show/move
void balls() {
  collision( a, b );
  collision( a, c );
  collision( a, d );
  collision( a, e );
  //if (cue.dx > 0) {
  collision( a, cue);
  //}
  //
  collision( b, c );
  collision( b, d );
  collision( b, e );
  //if (cue.dx > 0) {
  collision( b, cue);
  //}
  //
  collision( c, d );
  collision( c, e );
  //if (cue.dx > 0) {
  collision( c, cue);
  //}
  //
  collision( d, e ); 
  //if (cue.dx > 0) {
  collision( d, cue);
  //}
  //
  //if (cue.dx > 0) {
  collision( e, cue);
  //}
  
  a.show();
  b.show();
  c.show();
  d.show();
  e.show();
  cue.show();
  
  a.move();
  b.move();
  c.move();
  d.move();
  e.move();
  cue.move();
}

// Action when two balls collide (adds to score)
void collision( Ball p, Ball q ) {
  if ( p.hit( q.x,q.y ) ) {
    float tmp;
    tmp=p.dx;  p.dx=q.dx;  q.dx=tmp;      // Swap the velocities.
    tmp=p.dy;  p.dy=q.dy;  q.dy=tmp;
    score += 1;
  }
}

// Button class gives properties
class Button {
  float x,y,w,h;
  String name="";
  
  void show() {
    strokeWeight(1);
    stroke(0);
    fill(255,175,0);
    rectMode( CORNER );
    rect ( x,y,w,h, 7 );
    fill(0,0,0);
    text(name, x+7, y+14);
  }
}

//// Ball class gives properties
class Ball {
  float x,y, dx, dy;
  int r,g,b;
  int number;
  String name="";
  
  void show() {
    fill(r,g,b);
    ellipse( x,y, 30,30);
    fill(0);
    text(name, x-3,y+4);
  }
  void move() {
    // WALL COLLISION
    if (wall == true && x<middle+23) {
      dx *= -1;
    }
    
    // BOUNDARY COLLISIONS
    x += dx;
    if (x> right || x< left) { dx = -dx; }
    y += dy;
    if (y>bottom || y<top) { dy = -dy; }
  }

  void reset() {
  x= random(middle+40, right); y= random(top, bottom);
  dx=  random( -3,3 );   dy=  random( -3,3 );
  }
  
  // BALL COLLISIONS
  boolean hit( float x, float y ) {
    if (dist( x,y, this.x,this.y ) < 30 ) return true;
    else return false;
  }  
}
