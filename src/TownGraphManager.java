import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Town Manager
 * @author Lincoln D Maene
 */
public class TownGraphManager implements TownGraphManagerInterface {

    private TownGraph town_map=new TownGraph();

    /**
     * Adds a road with 2 towns and a road name
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @param roadName name of road
     * @return true if the road was added successfully
     */
    @Override
    public boolean addRoad(String town1, String town2, int weight, String roadName) {

        Road flag_helper=null;//used to decide wether road was successfully added
        boolean flag=false;

        flag_helper=town_map.addEdge(getTown(town1),getTown(town2),weight,roadName);

        if (flag_helper!=null)
            flag=true;

        return flag;
    }
    /**
     * Returns the name of the road that both towns are connected through
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @return name of road if town 1 and town2 are in the same road, returns null if not
     */
    @Override
    public String getRoad(String town1, String town2) {
        return town_map.getEdge(getTown(town1),getTown(town2)).getName();
    }
    /**
     * Adds a town to the graph
     * @param v the town's name  (lastname, firstname)
     * @return true if the town was successfully added, false if not
     */
    @Override
    public boolean addTown(String v) {

        return  town_map.addVertex(new Town(v));
    }
    /**
     * Gets a town with a given name
     * @param name the town's name
     * @return the Town specified by the name, or null if town does not exist
     */
    @Override
    public Town getTown(String name) {
        Town sought_town=null;

        if (town_map.containsVertex(new Town(name))){//if the town exists

            Set<Town> search_set=town_map.vertexSet();//use a set of all towns to find the one

            for (Town town:search_set){
                if(town.getName().equals(name))
                    sought_town=town;
            }

        }


        return sought_town;
    }
    /**
     * Determines if a town is already in the graph
     * @param v the town's name
     * @return true if the town is in the graph, false if not
     */
    @Override
    public boolean containsTown(String v) {
        return town_map.containsVertex(new Town(v));
    }
    /**
     * Determines if a road is in the graph
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @return true if the road is in the graph, false if not
     */
    @Override
    public boolean containsRoadConnection(String town1, String town2) {
        return town_map.containsEdge(getTown(town1),getTown(town2));
    }
    /**
     * Creates an arraylist of all road titles in sorted order by road name
     * @return an arraylist of all road titles in sorted order by road name
     */
    @Override
    public ArrayList<String> allRoads() {
        Set<Road> road_set=new HashSet<>(town_map.edgeSet());//use set to iterate over and add to arraylist

        ArrayList<String> road_strings=new ArrayList<>();

        for (Road e:road_set)
            road_strings.add(e.toString());

        Collections.sort(road_strings);//sort array
        return road_strings;
    }
    /**
     * Deletes a road from the graph
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @param road the road name
     * @return true if the road was successfully deleted, false if not
     */
    @Override
    public boolean deleteRoadConnection(String town1, String town2, String road) {

        boolean flag=false;//is the flag used

        Road flag_helper=town_map.removeEdge(getTown(town1),getTown(town2),1, road);//helper to flag

        if (flag_helper!=null)
            flag=true;

        return flag;
    }
    /**
     * Deletes a town from the graph
     * @param v name of town (lastname, firstname)
     * @return true if the town was successfully deleted, false if not
     */
    @Override
    public boolean deleteTown(String v) {
        return town_map.removeVertex(new Town(v));
    }
    /**
     * Creates an arraylist of all towns in alphabetical order (last name, first name)
     * @return an arraylist of all towns in alphabetical order (last name, first name)
     */
    @Override
    public ArrayList<String> allTowns() {

        Set<Town> road_set=new HashSet<>(town_map.vertexSet());//use set to iterate over and add to arraylist

        ArrayList<String> town_strings=new ArrayList<>();

        for (Town e:road_set)
            town_strings.add(e.toString());

        Collections.sort(town_strings);//sort array
        return town_strings;
    }

    /**
     * Returns the shortest path from town 1 to town 2
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @return an Arraylist of roads connecting the two towns together, null if the
     * towns have no path to connect them.
     */
    @Override
    public ArrayList<String> getPath(String town1, String town2) {


        return town_map.shortestPath(getTown(town1), getTown(town2));
    }

    /**
     * setter for town graph, used in my private tests
     * @param new_graph
     */
    public void set_town_graph(TownGraph new_graph){

       this.town_map=new_graph;
    }

    /**
     * build graph from a file
     * @param selectedFile is file from which town is built
     * @throws FileNotFoundException thrown if town not successfully built
     */
    public void populateTownGraph(File selectedFile) throws FileNotFoundException {

        Scanner graph_builder=new Scanner(selectedFile).useDelimiter(";|\\s");;//scanner used to read file lin by line
        Scanner road_builder=null;//scanner used to read road
        Set<Town> town_set=new HashSet<>();
        ArrayList<String> line_reader=new ArrayList<>();

        String town1;//source
        String town2;//destination
        int road_weight;//length of road
        String road_name;//name of road


        String town_finder;

        while (graph_builder.hasNext()){ //add the towns first

            town_finder=graph_builder.next();


            if (!town_finder.contains(",") && !town_finder.equals("")) {//add town skipping roads

                     town_set.add(new Town(town_finder));
            }



        }

        graph_builder.close();//close scanner temporarily



        for (Town t: town_set) //add towns to map
            town_map.addVertex(t);


        graph_builder=new Scanner(selectedFile);//open scanner again


        while (graph_builder.hasNext()){ //add each line first to arraylist

                    line_reader.add(graph_builder.nextLine());

              }


        graph_builder.close();//close scanner



        for (String line: line_reader){//reach each line from arraylist

            road_builder=new Scanner(line).useDelimiter(",|;");

            while (road_builder.hasNext()){//use road builder scanner to build each road

                road_name=road_builder.next();

                road_weight=Integer.parseInt(road_builder.next());
                town1=road_builder.next();

                town2=road_builder.next();



                addRoad(town1,town2,road_weight,road_name);



                }


        }




    }
}
