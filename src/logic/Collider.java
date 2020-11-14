package logic;

import main.Game;

public class Collider {
//
//    public boolean isBulletCollide(int x, int y, int dx, int dyTop, int dyBottom, int direction) {
//        if(isBulletCollideAtScreen(x)){
//            return true;
//        }
//        else if (direction == 1) {
//            return isUserBulletCollide(x, y, dx, dyTop, dyBottom);
//        } else if (direction == -1) {
//            return isBossBulletCollide(x, y, dx, dyTop, dyBottom);
//        }
//        return false;
//    }

    public boolean isUserBulletCollide(int bulletX, int bulletY, int dx, int dyTop, int dyBottom) {
        boolean isX = false, isY = false;
        if (bulletX >= dx) {
            isX = true;
            if (bulletY >= dyTop && bulletY <= dyBottom) {
                isY = true;
            }
        }
        return isX && isY;
    }

    public boolean isBossBulletCollide(int x, int y, int dx, int dyTop, int dyBottom) {
        boolean isX = false, isY = false;
        if (x <= dx) {
            isX = true;
            if (y >= dyTop && y <= dyBottom) {
                isY = true;
            }
        }
        return isX && isY;
    }

    public boolean isBulletCollideAtScreen(int x) {
        if (x > Game.WIDTH) {
            return true;
        } else if (x < 0) {
            return true;
        }
        return false;
    }
}
