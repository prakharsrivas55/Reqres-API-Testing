package api_endpoints;

public class Routes {
    
    public static String base_url="https://reqres.in";

    //User module

    public static String get_url= base_url+ "/api/users";
    
    public static String post_url= base_url + "/api/users";
    public static String put_url(int id){
        return  base_url+ "/api/users/"+ id;
    }
    public static String delete_url(int id){ 
        return base_url+ "/api/users/"+ id;
    }

}
