

import java.util.*;

/**
 * Implements Town Graph
 * @author Linconl D Maene
 */
public class TownGraph implements GraphInterface<Town, Road> {

    private Map<Town, HashSet<Road>> townMap=new HashMap<>();





    /**
     * Get road between two towns
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.     *
     * @return Road connecting two towns
     */
    @Override
    public Road getEdge(Town sourceVertex, Town destinationVertex) {
        Road wanted_road=null;//create road

        if (sourceVertex!=null && destinationVertex!=null)//if the towns are not null
        {
            HashSet <Road> roads_out=townMap.get(sourceVertex);//create reference to set

            for (Road e: roads_out){//check set for road from town to destination
                if (e.contains(sourceVertex) && e.contains(destinationVertex))
                    wanted_road=e;
            }

        }

        return wanted_road;
    }

    /**
     * add an edge to the graph
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @param weight weight of the edge
     * @param description description for edge
     *
     * @return the road added
     */
    @Override
    public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {

        if (sourceVertex==null||destinationVertex==null)//if the towns are null
            throw  new NullPointerException();
        if (!townMap.containsKey(sourceVertex)||!townMap.containsKey(destinationVertex))//if the towns are not in graph
            throw new IllegalArgumentException();

        Road newRoad=new Road(sourceVertex, destinationVertex, weight, description);//create road
        HashSet<Road> roadSet=townMap.get(sourceVertex);//add road to map


        roadSet.add(newRoad);

        roadSet=townMap.get(destinationVertex);//add same road to destination

        roadSet.add(newRoad);



        return newRoad;
    }

    /**
     *Add town to map
     * @param town is the town to be added
     * @return true if town was added
     */
    @Override
    public boolean addVertex(Town town) {

        boolean town_new=false;

        if (!townMap.containsKey(town))
            town_new=true;

        if (town_new){

            townMap.put(town, new HashSet<>());

        }

        return town_new;

    }

    /**
     * Test if road exists between towns
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     *
     * @return true if road exists
     */
    @Override
    public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
        boolean road_contained=false;

        Road road_out=getEdge(sourceVertex,destinationVertex);

        if (road_out!=null)
            road_contained=true;

        return road_contained;
    }

    /**
     * Check if map contains town
     * @param town is town to be found
     * @return true if town is in map
     */
    @Override
    public boolean containsVertex(Town town) {
        return townMap.containsKey(town);
    }

    /**
     * create set of all roads in map
     * @return set of all roads
     */
    @Override
    public Set<Road> edgeSet() {
        HashSet <Road> bigSet=new HashSet();//create big set
        HashSet <Town> townSet=new HashSet<>(townMap.keySet());//create set of towns

        for (Town e: townSet){
            bigSet.addAll(townMap.get(e));//merge two sets

        }

        return bigSet;
    }

    /**
     * find all roads connected to a given town
     * @param vertex the vertex for which a set of touching edges is to be
     * returned.
     *
     * @return a set of roads
     */
    @Override
    public Set<Road> edgesOf(Town vertex) {
        return townMap.get(vertex);
    }

    /**
     * remove road
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @param weight weight of the edge
     * @param description description of the edge
     *
     * @return removed road
     */
    @Override
    public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {


        Road removedRoad=null;

        if (weight>-1 && description!=null){//if weight is at least zero and road has name

            removedRoad=getEdge(sourceVertex,destinationVertex);//create pointer to edje

            HashSet<Road> road_set=townMap.get(sourceVertex);//create pointer to town roadSet

            road_set.remove(removedRoad);//point to source

            road_set=townMap.get(destinationVertex);

            road_set.remove(removedRoad);//point to destination


        }


        return removedRoad;
    }

    /**
     * Remove town from map
     * @param town is the town to be removed
     * @return true if town removed
     */
    @Override
    public boolean removeVertex(Town town) {

        boolean removed=false;
        if(townMap.containsKey(town)){//if town exists

            HashSet <Road> deletedRoad=new HashSet<>(edgesOf(town));//create a set of deleted Roads

            for (Road e:deletedRoad)//iterate through map and remove roads
                removeEdge(e.getSource(),e.getDestination(),e.getWeight(),e.getName());


            townMap.remove(town);//remove town
            removed=true;

        }

        return removed;
    }

    /**
     * find set of towns
     * @return all towns in set
     */
    @Override
    public Set<Town> vertexSet() {
        return townMap.keySet();
    }

    /**
     * find shortest path via djistra's algortih develloped via: https://www.youtube.com/watch?v=gdmfOwyQlcI
     * @param sourceVertex starting vertex
     * @param destinationVertex ending vertex
     * @return shortest path from source to destination on map
     */
    @Override
    public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
        Set<Town> unvisited_towns=new HashSet<>(vertexSet());//get a set of all univisited towns
        Set<Town> visited_towns=new HashSet<>();//get a set of all visited towns
        Set<Town> unvisited_neighbors;//unvisited neighbors of current town
        List<Town> shortest_path=new ArrayList<>();//linkedlist of shORTEST path
        ArrayList<String> returned_path=new ArrayList<>();//we need to return strings


        Town current_town=sourceVertex;//get pointer to start town

        for (Town e: unvisited_towns)
            e.setDistance(Integer.MAX_VALUE,null);;//set starting distances

        current_town.setDistance(0, sourceVertex);


        unvisited_towns.remove(current_town);//remove from unvisited towns
        visited_towns.add(current_town);

        while (!visited_towns.contains(destinationVertex)){

            unvisited_neighbors=new HashSet<>(current_town.get_adjecent_towns());


            for (Town e: unvisited_neighbors){
                int tentative_dist=(current_town.getDistance().get_value()+getEdge(current_town,e).getWeight());

                if (tentative_dist<e.getDistance().get_value())
                    e.setDistance(tentative_dist, current_town);
            }

            unvisited_towns.remove(current_town);//remove from unvisited towns
            visited_towns.add(current_town);

            if (!unvisited_towns.isEmpty())
                current_town=Collections.min(unvisited_towns);


        }

        current_town=destinationVertex;

        while (!shortest_path.contains(sourceVertex)){//find shortest path by tracing back from destination via recorded distance
            shortest_path.add(current_town);

            current_town=current_town.getDistance().get_town_from();
        }

        Collections.reverse(shortest_path);//reverse order so we start from begining instead of destination

        for (Town shortest_town: shortest_path) {    //we need to return strings

            if (!shortest_town.equals(sourceVertex)) {//we start from second town
                Road connecting_road = getEdge(shortest_town, shortest_town.getDistance().get_town_from());//road connecting two towns
                Town town_from = shortest_town.getDistance().get_town_from();//the town of origin
                returned_path.add(town_from + " via " + connecting_road + " to " + shortest_town + " " + connecting_road.getWeight()+" mi");//add to string in proper format

            }



        }
        return returned_path;
    }

    /**
     * This method does nothing, not useful but part of javadod provided
     * @param sourceVertex the vertex to find shortest path from
     */
    public void dijkstraShortestPath(Town sourceVertex) {

    }

    public Map printMap(){
        return townMap;
    }

}
