import math
import time

airDensity = 1.28

meterInPixel = 243;

throwHeight = 1.85
wind = .3
v_x = 5.38
v_y = 0.948
planeWeight = 0.008
wingArea = 0.022

X = 0
Y = throwHeight

angle = 0

lastTick = time.time()
ticks = 0

secondsInAir = 0

finished = False

secondsPerTick = 1.0/60.0 #how many seconds in a tick

def mag(x, y):
    math.sqrt(x*x + y*y);


def getDragCoefficient(rot):
    Fd = planeWeight * 9.81 * math.sin(rot);
    p = airDensity;
    A = wingArea;
    v = mag(v_x, v_y);

    Cd = (2*Fd)/(A * p * (v*v));
    return math.isnan(Cd) and Cd or 0.0001;

def getLiftCoefficient(rot):
    Fl = planeWeight * 9.81 * math.sin(rot);
    p = airDensity;
    A = wingArea;
    v = mag(v_x, v_y);

    Cl = (2*Fl)/(A * p * (v*v));
    return math.isnan(Cl) and Cl or 0.0001;

def getLift():
    return .5 * getLiftCoefficient(angle) * airDensity * wingArea * math.pow(mag(v_x, v_y), 2);

def getDrag():
    return .5 * getDragCoefficient(angle) * airDensity * wingArea * math.pow(mag(v_x, v_y), 2);

def getWeight():
        return planeWeight * (9.81*1.9);

def newtonsToMetersPerSecond(newtons, t):
    a = newtons/planeWeight;
    v = a*t;
    return v;

def updateRot():
    xDiff = v_x
    if (xDiff == 0):
        return 0.0;
    
    yDiff = v_y;
    return math.atan(yDiff/xDiff);

def tick(): #run this code very frame
    global ticks, v_x, v_y, X, Y, lastTick, secondsInAir
    ticks += 1

    if finished: 
        return

    timeBefore = lastTick
    lastTick = time.time()

    if ticks % 1.5 != 0: 
        return
    
    updateRot()

    secondsInAir += lastTick-timeBefore

    dragNewtons = getDrag()
    liftNewtons = getLift()
    weightNewtons = getWeight()

    dragNewtons = abs(dragNewtons);
    liftNewtons = abs(liftNewtons);
    weightNewtons = abs(weightNewtons);  

    dragVelImpact = newtonsToMetersPerSecond(dragNewtons, secondsInAir) * secondsPerTick
    liftVelImpact = newtonsToMetersPerSecond(liftNewtons, secondsInAir) * secondsPerTick
    weightVelImpact = newtonsToMetersPerSecond(weightNewtons, secondsInAir) * secondsPerTick

    if dragVelImpact > v_x:
        dragVelImpact = v_x

    v_x -= dragVelImpact
    v_y += liftVelImpact

    v_y -= weightVelImpact

    v_x -= wind * secondsPerTick

    X += v_x
    Y += v_y

    if Y <= 0: 
        finished = True