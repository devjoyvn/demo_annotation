public class Main {

    public static void main(String[] args) {

        ProcessAnnotation processAnnotation = new ProcessAnnotation();

        System.out.println("Tesing........");
        System.out.println("Case 1:");
        User user1 = new User("shareprogramming.net", -1, "Dong Nai");
        try {
            String json = processAnnotation.exportToJson(user1);
            System.out.println(json);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Case 2:");


        User user2 = new User("  shareprogramming.net ", 24, "Ho Chi Minh");
        try {
            String json = processAnnotation.exportToJson(user2);
            System.out.println(json);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
