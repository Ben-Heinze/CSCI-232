public class Country implements Comparable<Country> {
    private String continent, location, date;
    private long totalCases, newCases, population;

    public Country(String continent, String location, String date, long totalCases, long newCases, long population){
        this.continent = continent;
        this.location = location;
        this.date = date;
        this.totalCases = totalCases;
        this.newCases = newCases;
        this.population = population;
    }//constructor



    //useless print function
    public void print(){
        System.out.println(continent+"\t"+location+"\t"+date+"\t"+totalCases+"\t"+newCases+"\t"+population);
    }

    public String toString(){
        if(location==null){
            location="";
        }
        return "New Cases: "+newCases+" at "+location+"/"+continent+" on "+date+" Total: "+totalCases+" Pop:"+population;
    }

    public long getNewCases(){
        return newCases;
    }


    /**
     * When compareTo is used on this class, it will compare the number of new Cases.
     * @param other the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Country other) {
        if(this.newCases > other.newCases) return 1; //if this.newCases is greater than the parameter, return 1
        else if(this.newCases < other.newCases) return -1;
        return 0;//if equal, return 0

    }
}//class
