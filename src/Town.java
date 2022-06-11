import java.util.ArrayList;
import java.util.List;

/**
 * Town class
 * @author  Lincoln D Maene
 */
public class Town extends java.lang.Object  implements java.lang.Comparable<Town>{

    /**
     * Private class used to generate Dijtra distance
     */
    class Distance implements Comparable<Distance>{

        private Integer distance;
        private Town town_from;

        /**
         *distance constructor
         * @param distance distance of town
         * @param town_from town from which distance is calculated
         */
        public Distance (Integer distance, Town town_from){
            this.distance=distance;
            this.town_from=town_from;
        }

        /**
         * get town from which distance measured
         * @return town from which distance is measured
         */
        public Town get_town_from(){
            return town_from;
        }

        public int get_value(){
            return distance;
        }

        /**
         * Compare two distances
         * @param compared_distance is the distance compared
         * @return usual stuff from api
         */
        @Override
        public int compareTo(Distance compared_distance) {
            return this.distance.compareTo(compared_distance.distance);
        }
        @Override
        public String toString(){
           return distance+"-->"+town_from;
        }
    }

    private String name;//name of the town
    private List adjacent_towns;
    private Distance distance;//used for dijstra's algorithm



    /**
     * Constructor
     * @param name is the name of the town
     */
    public Town(String name){

        this.name=name;
        this.adjacent_towns=new ArrayList<Town>();


    }

    /**
     * Copy constructor
     * @param template is the copied town
     */
    public Town (Town template){

        this.name=template.getName();
        this.adjacent_towns=new ArrayList(template.get_adjecent_towns());


    }

    /**
     *Override hashcode
     * @return hashcode of town name
     */
    @Override
    public int hashCode(){
        return name.hashCode();
    }

    /**
     * Check equality of towns
     * @param comparedTown is the town to be compared
     * @return true if two towns are equal
     */
    public boolean equals(java.lang.Object comparedTown){
        return comparedTown.hashCode()==this.hashCode();
    }


    /**
     * Getter for adjacent towns
     * @return adjacent towns
     */
    public List<Town> get_adjecent_towns(){
        return this.adjacent_towns;
    }

    /**
     * add another adjacent town
     * @param town is a town to be added
     */
    public void add_adjacent_towns(Town town){
        this.adjacent_towns.add(town);
        town.get_adjecent_towns().add(this);
    }
    /**
     * Getter for name
     * @return name of the town
     */
    public String getName(){
        return name;
    }

    /**
     * toString method
     * @return town name
     */
    public String toString(){
        return this.name;
    }

    /**
     * get distance for dijtra's algo
     * @return
     */
    public Distance getDistance(){
        return distance;
    }

    /**
     * Set distance for dijtra's algo
     * @param distance is value of distance
     */
    public void setDistance(Integer distance, Town town_from){
        this.distance=new Distance(distance,town_from);
    }


    /**
     *
     * @param comparedTown is the town to be compared
     * @return 0 is towns are equal
     */
    @Override
    public int compareTo(Town comparedTown) {

        int comparison=-47;//variable holding comparison value

        if (this.getDistance().compareTo(comparedTown.getDistance())>0)//if this town is greater
            comparison=47;

        if (comparedTown.equals(this))
            comparison=0;



        return comparison;
    }
}
