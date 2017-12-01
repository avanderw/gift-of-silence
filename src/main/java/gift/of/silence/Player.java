package gift.of.silence;

import java.util.*;

class Player {

    static int width = 23;
    static int height = 21;
    static int shipLength = 3;
    static int shipWidth = 1;
    static int maxCarry = 100;
    static int maxSpeed = 2;
    static int maxDelay = 4;
    static int mineDirect = 25;
    static int mineIndirect = 10;
    static int mineVisibility = 5;
    static int cannonMaxDistance = 10;
    static int cannonDirect = 50;
    static int cannonIndirect = 25;

    static String move = "MOVE";
    static String wait = "WAIT";
    static String slower = "SLOWER";
    static String mine = "MINE";
    static String fire = "FIRE";

    static int MINE = 1;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        while (true) {
            List<Ship> myShips = new ArrayList();
            List<Barrel> barrels = new ArrayList();
            List<Ship> theirShips = new ArrayList();

            int myShipCount = in.nextInt(); // the number of remaining ships
            int entityCount = in.nextInt(); // the number of entities (e.g. ships, mines or cannonballs)
            System.err.println(String.format("myShipCount:%s, entityCount:%s", myShipCount, entityCount));
            for (int i = 0; i < entityCount; i++) {
                int entityId = in.nextInt();
                // System.err.println(String.format("entityId:%s", entityId));
                String type = in.next();
                switch (type) {
                    case "SHIP":
                        Ship ship = new Ship(entityId);
                        ship.x = in.nextInt();
                        ship.y = in.nextInt();
                        ship.rotation = Rotation.values()[in.nextInt()];
                        ship.speed = in.nextInt();
                        ship.rum = in.nextInt();

                        if (in.nextInt() == MINE) {
                            myShips.add(ship);
                        } else {
                            theirShips.add(ship);
                        }
                        System.err.println(String.format("mine %s", myShips));
                        System.err.println(String.format("thiers %s", theirShips));
                        break;
                    case "BARREL":
                        Barrel barrel = new Barrel(entityId);
                        barrel.x = in.nextInt();
                        barrel.y = in.nextInt();
                        barrel.rum = in.nextInt();
                        in.nextInt();
                        in.nextInt();
                        in.nextInt();

                        barrels.add(barrel);
                        // System.err.println(String.format("adding %s", barrel));
                        break;
                    case "CANNONBALL":
                        Ball ball = new Ball(entityId);
                        ball.x = in.nextInt();
                        ball.y = in.nextInt();
                        ball.fired = in.nextInt();
                        ball.impact = in.nextInt();
                        in.nextInt();
                        in.nextInt();
                        System.err.println(String.format("adding %s", ball));
                        break;
                    case "MINE":
                        Mine mine = new Mine(entityId);
                        mine.x = in.nextInt();
                        mine.y = in.nextInt();
                        in.nextInt();
                        in.nextInt();
                        in.nextInt();
                        in.nextInt();
                        break;
                    default:
                        System.err.println(String.format("ERROR type:%s", type));
                }

            }

            myShips.forEach(myShip -> {
                // consider changing to highest priority barrel
                Barrel closestBarrel = null;
                if (!barrels.isEmpty()) {
                    closestBarrel = barrels.stream().min(Comparator.comparingDouble(barrel -> dist(barrel, myShip))).get();
                }
                
                Ship closestEnemy = theirShips.stream().min(Comparator.comparingDouble(theirShip->dist(theirShip, myShip))).get();
                
                if (enemyInRange){
                    if (headingClosestBarrel) {
                        fireEnemy
                    } else {
                        courseCorrectBarrel
                    }
                } else {
                    if (goodHealth) {
                        courseCorrectEnemy
                    } else {
                        courseCorrectBarrel
                    }
                }
                
                if (closestBarrel != null) {
                    if (turnsToReach(dist(closestEnemy, myShip)) < 2) {
                        System.out.println(String.format("FIRE %s %s", closestEnemy.x, closestEnemy.y));
                    } else {
                        System.out.println(String.format("MOVE %s %s", closestBarrel.x, closestBarrel.y));
                    }
                } else {
                    System.out.println(String.format("FIRE %s %s", closestEnemy.x, closestEnemy.y));
                }
                
            });
        }

    }

    static Double dist(Entity e1, Entity e2) {
        return Math.sqrt(Math.pow(e1.x - e2.x, 2) + Math.pow(e1.y - e2.y, 2));
    }

    static int turnsToReach(Double distance) {
        return (int) Math.floor(1 + distance / 3);
    }

    static class Entity {

        int entityId;

        int x;
        int y;

    }

    static class Mine extends Entity {

        Mine(int entityId) {
            this.entityId = entityId;
        }
    }

    static class Ball extends Entity {

        int fired;
        int impact;

        private Ball(int entityId) {
            this.entityId = entityId;
        }

        @Override
        public String toString() {
            return String.format("ball[x:%s, y:%s, impact:%s]", x, y, impact);
        }
    }

    static class Ship extends Entity {

        Rotation rotation;
        int speed;
        int rum;

        private Ship(int entityId) {
            this.entityId = entityId;
        }

        @Override
        public String toString() {
            return String.format("ship[x:%s, y:%s, rotation:%s, speed:%s, rum:%s]", x, y, rotation, speed, rum);
        }
    }

    static class Barrel extends Entity {

        int rum;

        private Barrel(int entityId) {
            this.entityId = entityId;
        }

        @Override
        public String toString() {
            return String.format("barrel[x:%s, y:%s, rum:%s]", x, y, rum);
        }
    }

    enum Rotation {
        EAST,
        NORTH_EAST,
        NORTH_WEST,
        WEST,
        SOUTH_WEST,
        SOUTH_EAST
    }
}
