/**
 * Road class for the graph edges
 * @author Lincoln D Maene
 */
public class Road implements java.lang.Comparable<Road> {


    private Town source, destination; //endpoints of the road
    private  int weight;//length of road
    private  String name;//name of road
    private  int degrees;//angle of road
    private boolean travelled=false;//used for djistra's

    /**
     * Constructor
     * @param source is the source town
     * @param destination destination town
     * @param name road name
     */
    public Road(Town source,Town destination,String name){
        this.source=source;
        this.destination=destination;
        this.weight=1;
        this.name=name;
        source.add_adjacent_towns(destination);
    }

    /**
     * Constructor
     * @param source is the source town
     * @param destination destination town
     * @param weight length or road
     * @param degrees road angle
     * @param name road name
     */
    public Road(Town source,Town destination,int weight, int degrees, String name){
        this (source, destination,name);
        this.degrees=degrees;
        this.weight=weight;

    }

    /**
     * Another constructor
     * @param source is the source town
     * @param destination destination town
     * @param weight length or road
     * @param name  road name
     */
    public Road(Town source,Town destination,int weight, String name){
        this (source, destination,name);
        this.degrees=degrees;
        this.weight=weight;

    }

    /**
     * check if edge contains town
     * @param town is the town to be checked
     * @return true if the edge contains the town
     */
    public boolean contains(Town town){

        return this.source.equals(town) || this.destination.equals(town);
    }

    public java.lang.String toString(){

        return this.name;

    }

    /**
     * Get source
     * @return town source of road
     */
    public Town getSource() {
        return source;
    }

    /**
     * get destination
     * @return town destination of road
     */
    public Town getDestination() {
        return destination;
    }

    /**
     * Get distance
     * @return lenght of road
     */
    public int getWeight() {
        return weight;
    }

    /**
     * get name of road
     * @return name of road
     */
    public String getName() {
        return name;
    }

    /**
     * get angle of road
     * @return angle of road
     */
    public  int getDegrees(){
        return degrees;
    }

    /**
     * equals method
     * @param object is the object compared
     * @return true if roads are equal
     */
    public boolean equals(java.lang.Object object){
        Road compared_road=(Road)object;

        return compared_road.contains(getSource()) && compared_road.contains(getDestination());


    }
    /**
     * compare two roads
     * @param road is the road compared
     * @return 42 if roads are not the same
     */
    @Override
    public int compareTo(Road road) {

        int relation=42;

        if (this.name.equals(road.getName()))
            relation=0;

        return relation;
    }

    /**
     * check if road was travelled
     * @return true if travelled
     */
    public boolean was_travelled(){
        return travelled;
    }

    /**
     * set road as travelled or not
     * @param travelled is wether road was travelled
     *
     */
    public void set_travelled(boolean travelled){
        this.travelled=travelled;
    }
}
