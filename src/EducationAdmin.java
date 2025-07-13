import service.*;
import service.Impl.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;
public class EducationAdmin {
    private final Scanner scanner;
    private static String role;
    private final CourseService courseService;
    private final TopicService topicService;
    private final TrainerService trainerService;
    private final StudentService studentService;
    private final EnrollmentService enrollmentService;
    private final FreeCourseOfferService freeCourseOfferService;
    private final TrainerSpecializationService trainerSpecializationService;
    private final BatchVenueService batchVenueService;
    private final BatchService batchService;
    private final BatchSessionTrackerService sessionService;
    public EducationAdmin() {
        this.scanner = new Scanner(System.in);
        this.courseService = new CourseServiceImpl();
        this.trainerService = new TrainerServiceImpl();
        this.topicService = new TopicServiceImpl();
        this.studentService = new StudentServiceImpl();
        this.enrollmentService = new EnrollmentServiceImpl();
        this.freeCourseOfferService = new FreeCourseOfferServiceImpl();
        this.trainerSpecializationService = new TrainerSpecializationServiceImpl();
        this.batchVenueService = new BatchVenueServiceImpl();
        this.batchService = new BatchServiceImpl();
        this.sessionService = new BatchSessionTrackerServiceImpl();
    }
    public static void main(String[] args) {
        EducationAdmin system = new EducationAdmin();
        system.startSystem();
    }
    public void startSystem() {
        System.out.println("\n=====================================");
        System.out.println("Welcome to Course Monitoring System");
        System.out.println("=====================================");
        System.out.println("1. Login (Admin/Trainer)");
        System.out.println("2. Exit");
        System.out.print("Enter your option: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        if (option == 1) {
            loginUser();
        } else if (option == 2) {
            System.out.println("Exiting...");
            System.exit(0);
        } else {
            System.out.println("Invalid choice. Try again.");
            startSystem();
        }
    }
    public void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        role = trainerService.login(username, password);

        System.out.println("============================================");
        if (role == null) {
            System.out.println("Invalid credentials or access denied!");
            System.out.println("============================================");
            startSystem();
        } else {
            System.out.println("Login successful! Welcome, " + role + ".");
            System.out.println("============================================");

            if (role.equalsIgnoreCase("Admin")) {
                showAdminMenu();
            } else if (role.equalsIgnoreCase("Trainer")) {
                showTrainerMainMenu();
            }
        }
    }
    public void showAdminMenu() {
        System.out.println("\nAdmin Menu - Choose an option:");
        System.out.println("1. Course");
        System.out.println("2. Topic");
        System.out.println("3. Student");
        System.out.println("4. Enrollment");
        System.out.println("5. Free Course Offer");
        System.out.println("6. Trainer");
        System.out.println("7. Trainer Specialization");
        System.out.println("8. Batch Venue");
        System.out.println("9. Batch");
        System.out.println("10. Batch Session Tracker");
        System.out.println("11. Exit");
        System.out.print("Enter your option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                showCourseMenu();
                break;
            case 2:
                showTopicMenu();
                break;
            case 3:
                showStudentMenu();
                break;
            case 4:
                showEnrollmentMenu();
                break;
            case 5:
                showFreeCourseOfferMenu();
                break;
            case 6:
                showTrainerMenu();
                break;
            case 7:
                showTrainerSpecializationMenu();
                break;
            case 8:
                showBatchVenueMenu();
                break;
            case 9:
                showBatchMenu();
                break;
            case 10:
                showBatchSessionTrackerMenu();
                break;
            case 11:
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Try again.");
                showAdminMenu();
        }
    }

    public void showTrainerMainMenu() {
        System.out.println("Trainer Menu - Choose an option");
        System.out.println("1. View All Courses");
        System.out.println("2. View All Topics");
        System.out.println("3. Student");
        System.out.println("4. Enrollment");
        System.out.println("5. View Free Course Offer");
        System.out.println("6. Update Trainer Details");
        System.out.println("7. View All Batches");
        System.out.println("8. Update Batch session tracker");
        System.out.println("9. Exit");
        System.out.println("Enter your option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                viewCourses();
                break;
            case 2:
                viewTopics();
                break;
            case 3:
                showStudentMenu();
                break;
            case 4:
                showEnrollmentMenu();
                break;
            case 5:
                viewOffers();
                break;
            case 6:
                updateTrainer();
                break;
            case 7:
                viewBatch();
                break;
            case 8:
                updateSession();
                break;
            case 9:
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Try again.");
                showTrainerMainMenu();
        }
    }

    public void showBatchSessionTrackerMenu() {
        System.out.println("Choose an option");
        System.out.println("1. Add Session");
        System.out.println("2. Delete Session");
        System.out.println("3. Update Session");
        System.out.println("4. View All Sessions");
        System.out.println("5. Back to Admin Menu");
        System.out.println("Enter your choice: ");

        int option = scanner.nextInt();
        scanner.nextLine();
        switch(option) {
            case 1:
                addSession();
                break;
            case 2:
                deleteSession();
                break;
            case 3:
                updateSession();
                break;
            case 4:
                viewSession();
                break;
            case 5:
                showAdminMenu();
                return;
            default:
                System.out.println("Invalid choice. Try again.");
        }
        showBatchSessionTrackerMenu(); // Recursive call
    }

    public void updateSession() {
        Map<String, Object> updateValues = new HashMap<>();
        System.out.println("Enter Session ID:");
        int sessionId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        updateValues.put("id", sessionId);

        if (confirmUpdate("update Batch ID")) {
            System.out.println("Enter new Batch ID:");
            updateValues.put("batchId", scanner.nextInt());
            scanner.nextLine();
        }

        if (confirmUpdate("update Session Date (YYYY-MM-DD)")) {
            System.out.println("Enter new Session Date:");
            updateValues.put("sessionDate", scanner.nextLine());
        }

        if (confirmUpdate("update Topic ID")) {
            System.out.println("Enter new Topic ID:");
            updateValues.put("topicId", scanner.nextInt());
            scanner.nextLine();
        }

        if (confirmUpdate("update Session Status (Pending/Ongoing/Completed)")) {
            System.out.println("Enter new Status:");
            updateValues.put("status", scanner.nextLine());
        }

        String resultMessage = sessionService.update(updateValues);
        System.out.println("==================================================================");
        System.out.println(resultMessage);
        System.out.println("==================================================================");
        if (role.equalsIgnoreCase("trainer")) {
            showTrainerMainMenu();
        }
    }

    public void viewSession() {
        List<Map<String, Object>> allSessions = sessionService.getAllSessions();
        System.out.println("Fetching All Sessions:");
        System.out.println("--------------------------------------------------------------------------------");

        for (Map<String, Object> session : allSessions) {
            System.out.println("Session ID: " + session.get("id"));
            System.out.println("Batch ID: " + session.get("batchId"));
            System.out.println("Session Date: " + session.get("sessionDate"));
            System.out.println("Topic ID: " + session.get("topicId"));
            System.out.println("Status: " + session.get("status"));
            System.out.println("--------------------------------------------------------------------------------");
        }
    }

    public void deleteSession() {
        Map<String, Object> deleteValues = new HashMap<>();
        System.out.println("Enter Session ID : ");
        deleteValues.put("sessionId", scanner.nextInt());
        scanner.nextLine();

        boolean isDeleted = sessionService.delete(deleteValues);
        if (isDeleted) {
            System.out.println("============================================");
            System.out.println("Session Deleted Successfully...");
            System.out.println("============================================");
        } else {
            System.out.println("=============================================================================");
            System.out.println("Unable to delete session. Please check if the Session ID is correct.");
            System.out.println("=============================================================================");
        }
    }

    public void addSession() {
        Map<String, Object> addValues = new HashMap<>();

        System.out.println("Enter Batch ID: ");
        addValues.put("batchId", scanner.nextInt());
        scanner.nextLine();

        System.out.println("Enter Session Date (YYYY-MM-DD): ");
        addValues.put("sessionDate", scanner.nextLine());

        System.out.println("Enter Topic ID: ");
        addValues.put("topicId", scanner.nextInt());
        scanner.nextLine();

        System.out.println("Enter Session Status (Pending/Ongoing/Completed): ");
        addValues.put("status", scanner.nextLine());

        String resultMessage = sessionService.add(addValues);
        System.out.println("==================================================================");
        System.out.println(resultMessage);
        System.out.println("==================================================================");
    }

    public void showBatchMenu() {
        System.out.println("Choose an option");
        System.out.println("1. Add Batch");
        System.out.println("2. Delete Batch");
        System.out.println("3. Update Batch");
        System.out.println("4. View All Batches");
        System.out.println("5. Back to Admin Menu");
        System.out.println("Enter your choice: ");

        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                addBatch();
                break;
            case 2:
                deleteBatch();
                break;
            case 3:
                updateBatch();
                break;
            case 4:
                viewBatch();
                break;
            case 5:
                showAdminMenu();
                return;
            default:
                System.out.println("Invalid choice. Try again.");
        }
        showBatchMenu(); // Recursive call
    }

    public void updateBatch() {
        Map<String, Object> updateValues = new HashMap<>();
        System.out.println("Enter Batch ID:");
        int batchId = scanner.nextInt();
        scanner.nextLine();
        updateValues.put("id", batchId);

        if (confirmUpdate("update Course ID")) {
            System.out.println("Enter new Course ID:");
            updateValues.put("courseId", scanner.nextInt());
            scanner.nextLine();
        }

        if (confirmUpdate("update Trainer ID")) {
            System.out.println("Enter new Trainer ID:");
            updateValues.put("trainerId", scanner.nextInt());
            scanner.nextLine();
        }

        if (confirmUpdate("update Batch Name")) {
            System.out.println("Enter new Batch Name:");
            updateValues.put("name", scanner.nextLine());
        }

        if (confirmUpdate("update Start Date (YYYY-MM-DD)")) {
            System.out.println("Enter new Start Date:");
            updateValues.put("startDate", scanner.nextLine());
        }

        if (confirmUpdate("update End Date (YYYY-MM-DD)")) {
            System.out.println("Enter new End Date:");
            updateValues.put("endDate", scanner.nextLine());
        }

        if (confirmUpdate("update Class Mode (Classroom/Online)")) {
            System.out.println("Enter new Class Mode:");
            updateValues.put("classMode", scanner.nextLine());
        }

        if (confirmUpdate("update Venue ID")) {
            System.out.println("Enter new Venue ID:");
            updateValues.put("venueId", scanner.nextInt());
            scanner.nextLine();
        }

        if (confirmUpdate("update Active Status (true/false)")) {
            System.out.println("Is the batch active?");
            updateValues.put("isActive", scanner.nextBoolean());
            scanner.nextLine();
        }

        String resultMessage = batchService.updateBatch(updateValues);
        System.out.println("==================================================================");
        System.out.println(resultMessage);
        System.out.println("==================================================================");
    }

    public void viewBatch() {
        List<Map<String, Object>> allBatches = batchService.getAllBatches();
        System.out.println("Fetching All Batches:");
        System.out.println("--------------------------------------------------------------------------------");

        for (Map<String, Object> batch : allBatches) {
            System.out.println("Batch ID: " + batch.get("id"));
            System.out.println("Course ID: " + batch.get("courseId"));
            System.out.println("Trainer ID: " + batch.get("trainerId"));
            System.out.println("Batch Name: " + batch.get("batchName"));
            System.out.println("Start Date: " + batch.get("startDate"));
            System.out.println("End Date: " + batch.get("endDate"));
            System.out.println("Class Mode: " + batch.get("classMode"));
            System.out.println("Venue ID: " + batch.get("venueId"));
            System.out.println("Active Status: " + batch.get("isActive"));
            System.out.println("--------------------------------------------------------------------------------");
        }
        if (role.equalsIgnoreCase("trainer")) {
            showTrainerMainMenu();
        }
    }

    public void deleteBatch() {
        Map<String, Object> deleteValues = new HashMap<>();
        System.out.println("Enter Batch ID : ");
        deleteValues.put("batchId", scanner.nextInt());
        scanner.nextLine();
        boolean isDeleted = batchService.delete(deleteValues);
        if (isDeleted) {
            System.out.println("============================================");
            System.out.println("Batch Deleted Successfully...");
            System.out.println("============================================");
        } else {
            System.out.println("===============================================================================");
            System.out.println("Unable to delete batch.Please check if the batch ID is correct and not null");
            System.out.println("===============================================================================");
        }
    }

    public void addBatch() {
        Map<String, Object> addValues = new HashMap<>();

        System.out.println("Enter Course ID: ");
        addValues.put("courseId", scanner.nextInt());
        scanner.nextLine();

        System.out.println("Enter Trainer ID: ");
        addValues.put("trainerId", scanner.nextInt());
        scanner.nextLine();

        System.out.println("Enter Batch Name: ");
        addValues.put("name", scanner.nextLine());

        System.out.println("Enter Start Date (YYYY-MM-DD): ");
        addValues.put("startDate", scanner.nextLine());

        System.out.println("Enter End Date (YYYY-MM-DD): ");
        addValues.put("endDate", scanner.nextLine());

        System.out.println("Enter Class Mode (Classroom/Online): ");
        addValues.put("classMode", scanner.nextLine());

        System.out.println("Enter Venue ID: ");
        addValues.put("venueId", scanner.nextInt());
        scanner.nextLine();

        System.out.println("Is the batch active? (true/false): ");
        addValues.put("isActive", scanner.nextBoolean());
        scanner.nextLine();

        String resultMessage = batchService.add(addValues);

        System.out.println("==================================================================");
        System.out.println(resultMessage);
        System.out.println("==================================================================");
    }

    public void showBatchVenueMenu() {
        System.out.println("Choose an option");
        System.out.println("1. Add Batch Venue");
        System.out.println("2. Delete Batch Venue");
        System.out.println("3. Update Batch Venue");
        System.out.println("4. View All Batch Venues");
        System.out.println("5. Back to Admin Menu");
        System.out.println("Enter your choice: ");

        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                addBatchVenue();
                break;
            case 2:
                deleteBatchVenue();
                break;
            case 3:
                updateBatchVenue();
                break;
            case 4:
                viewBatchesVenue();
                break;
            case 5:
                showAdminMenu();
                return;
            default:
                System.out.println("Invalid choice. Try again.");
        }
        showBatchVenueMenu(); // Recursive call
    }

    public void viewBatchesVenue() {
        List<Map<String, Object>> allVenues = batchVenueService.getAllBatchVenues();
        System.out.println("Fetching All Batch Venues:");
        System.out.println("--------------------------------------------------------------------------------");
        for (Map<String, Object> venue : allVenues) {
            System.out.println("Venue Id: " + venue.get("id"));
            System.out.println("Venue Detail: " + venue.get("venueDetail"));
            System.out.println("--------------------------------------------------------------------------------");
        }
    }

    public void updateBatchVenue() {
        Map<String, Object> updateValues = new HashMap<>();
        System.out.println("Enter Venue ID :");
        int venueId = scanner.nextInt();
        scanner.nextLine();
        updateValues.put("id", venueId);

        if (confirmUpdate("Update Venue Detail")) {
            System.out.println("Enter new Venue Detail:");
            updateValues.put("venueDetail", scanner.nextLine());
        }

        String resultMessage = batchVenueService.update(updateValues);
        System.out.println("==================================================================");
        System.out.println(resultMessage);
        System.out.println("==================================================================");
    }

    public void deleteBatchVenue() {
        Map<String, Object> deleteValues = new HashMap<>();
        System.out.println("Enter Venue ID : ");
        deleteValues.put("venueId", scanner.nextInt());
        scanner.nextLine();
        boolean isDeleted = batchVenueService.delete(deleteValues);
        if (isDeleted) {
            System.out.println("============================================");
            System.out.println("Batch Venue Deleted Successfully...");
            System.out.println("============================================");
        } else {
            System.out.println("========================================================================================");
            System.out.println("Unable to delete batch venue.Please check if the batch venue ID is correct and not null");
            System.out.println("========================================================================================");
        }
    }

    public void addBatchVenue() {
        Map<String, Object> addValues = new HashMap<>();
        System.out.println("Enter Venue Detail(Room Number or Meeting Link): ");
        addValues.put("venueDetail", scanner.nextLine());

        String resultMessage = batchVenueService.add(addValues);
        System.out.println("==================================================================");
        System.out.println(resultMessage);
        System.out.println("==================================================================");
    }

    public void showTrainerSpecializationMenu() {
        System.out.println("Choose an option");
        System.out.println("1. Add Trainer Specialization");
        System.out.println("2. Delete Trainer Specialization");
        System.out.println("3. Update Trainer Specialization");
        System.out.println("4. View All Trainer Specialization");
        System.out.println("5. Back to Admin Menu");
        System.out.println("Enter your choice: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                addTrainerSpecialization();
                break;
            case 2:
                deleteTrainerSpecialization();
                break;
            case 3:
                updateTrainerSpecialization();
                break;
            case 4:
                viewTrainersSpecialization();
                break;
            case 5:
                showAdminMenu();
                return;
            default:
                System.out.println("Invalid choice. Try again.");
        }
        showTrainerSpecializationMenu(); // Recursive call
    }

    public void viewTrainersSpecialization() {
        List<Map<String, Object>> allTrainerSpecialization = trainerSpecializationService.getAllTrainerSpecialization();
        System.out.println("Fetching All Trainer Specialization:");
        System.out.println("--------------------------------------------------------------------------------");
        for (Map<String, Object> trainerSpecialization : allTrainerSpecialization) {
            System.out.println("Trainer Specialization ID: " + trainerSpecialization.get("id"));
            System.out.println("Trainer Id: " + trainerSpecialization.get("trainerId"));
            System.out.println("Specialization: " + trainerSpecialization.get("specialization"));
            System.out.println("--------------------------------------------------------------------------------");
        }
    }

    public void updateTrainerSpecialization() {
        Map<String, Object> updateValues = new HashMap<>();
        System.out.println("Enter Trainer Specialization ID :");
        int trainerSpecializationId = scanner.nextInt();
        scanner.nextLine();
        updateValues.put("id", trainerSpecializationId);

        if (confirmUpdate("Update Trainer Id")) {
            System.out.println("Enter new Trainer Id:");
            updateValues.put("trainerId", scanner.nextInt());
            scanner.nextLine();
        }

        if (confirmUpdate("Update Specialization")) {
            System.out.println("Enter new Specialization:");
            updateValues.put("specialization", scanner.nextLine());
        }

        String resultMessage = trainerSpecializationService.update(updateValues);
        System.out.println("==================================================================");
        System.out.println(resultMessage);
        System.out.println("==================================================================");
    }

    public void deleteTrainerSpecialization() {
        Map<String, Object> deleteValues = new HashMap<>();
        System.out.println("Enter Trainer Specialization ID : ");
        deleteValues.put("trainerSpecializationId", scanner.nextInt());
        scanner.nextLine();
        boolean isDeleted = trainerSpecializationService.delete(deleteValues);
        if (isDeleted) {
            System.out.println("============================================");
            System.out.println("Trainer Specialization Deleted Successfully...");
            System.out.println("============================================");
        } else {
            System.out.println("==============================================================================================================");
            System.out.println("Unable to delete trainer specialization.Please check if the trainer specialization ID is correct and not null");
            System.out.println("==============================================================================================================");
        }
    }

    public void addTrainerSpecialization() {
        Map<String, Object> addValues = new HashMap<>();

        System.out.println("Enter Trainer ID: ");
        addValues.put("trainerId", scanner.nextInt());
        scanner.nextLine();
        System.out.println("Enter Specialization: ");
        addValues.put("specialization", scanner.nextLine());

        String resultMessage = trainerSpecializationService.add(addValues);
        System.out.println("==================================================================");
        System.out.println(resultMessage);
        System.out.println("==================================================================");
    }

    public void showTrainerMenu() {
		System.out.println("Choose an option");
        System.out.println("1. Add Trainer");
        System.out.println("2. Delete Trainer");
        System.out.println("3. Update Trainer");
        System.out.println("4. View All Trainers");
		System.out.println("5. Back to Admin Menu");
        System.out.println("Enter your choice: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                addTrainer();
                break;
            case 2:
                deleteTrainer();
                break;
            case 3:
                updateTrainer();
                break;
            case 4:
                viewTrainers();
                break;
            case 5:
                showAdminMenu();
                return;
            default:
                System.out.println("Invalid choice. Try again.");
        }
        showTrainerMenu(); // Recursive call
	}

    private void viewTrainers() {
        List<Map<String, Object>> allTrainers = trainerService.getAllTrainers();
        System.out.println("Fetching All Trainers:");
        System.out.println("--------------------------------------------------------------------------------");
        for (Map<String, Object> trainer : allTrainers) {
            System.out.println("Trainer ID: " + trainer.get("id"));
            System.out.println("Username: " + trainer.get("username"));
            System.out.println("Name: " + trainer.get("name"));
            System.out.println("Experience (years): " + trainer.get("experience"));
            System.out.println("Email: " + trainer.get("email"));
            System.out.println("Admin Status: " + trainer.get("isAdmin"));
            System.out.println("--------------------------------------------------------------------------------");
        }
    }

    public void addTrainer() {
		Map<String, Object> addValues = new HashMap<>();

        System.out.println("Enter Trainer Name: ");
        addValues.put("name", scanner.nextLine());

        System.out.println("Enter Username: ");
        addValues.put("username", scanner.nextLine());

        System.out.println("Enter Password: ");
        addValues.put("password", scanner.nextLine());

        System.out.println("Enter Experience (in years): ");
        addValues.put("experience", scanner.nextInt());
        scanner.nextLine();

        System.out.println("Enter Email: ");
        addValues.put("email", scanner.nextLine());

        System.out.println("Should this trainer be an admin? (yes/no): ");
        String isAdminInput = scanner.nextLine().trim().toLowerCase();
        boolean isAdmin = isAdminInput.equals("yes");

        addValues.put("isAdmin", isAdmin);

        String resultMessage = trainerService.addTrainer(addValues);

        System.out.println("==================================================================");
        System.out.println(resultMessage);
        System.out.println("==================================================================");
	}

    public void deleteTrainer() {
        Map<String, Object> deleteValues = new HashMap<>();
        System.out.println("Enter Trainer ID : ");
        deleteValues.put("trainerId", scanner.nextInt());
        scanner.nextLine();
        boolean isDeleted = trainerService.delete(deleteValues);
        if (isDeleted) {
            System.out.println("============================================");
            System.out.println("Trainer Deleted Successfully...");
            System.out.println("============================================");
        } else {
            System.out.println("===============================================================================");
            System.out.println("Unable to delete trainer.Please check if the trainer ID is correct and not null");
            System.out.println("===============================================================================");
        }
	}

    public void updateTrainer() {
		Map<String, Object> updateValues = new HashMap<>();
        System.out.println("Enter Trainer ID :");
        int trainerId = scanner.nextInt();
        scanner.nextLine();
        updateValues.put("id", trainerId);

        if (confirmUpdate("Update Trainer Name")) {
            System.out.println("Enter new Trainer Name:");
            updateValues.put("name", scanner.nextLine());
        }
        if (confirmUpdate("Update Experience")) {
            System.out.println("Enter new Experience(in years):");
            updateValues.put("experience", scanner.nextInt());
            scanner.nextLine();
        }
        if (confirmUpdate("Update Email")) {
            System.out.println("Enter new Email:");
            updateValues.put("email", scanner.nextLine());
        }
        if (confirmUpdate("Update Admin Status")) {
            System.out.println("Should this trainer be an admin? (yes/no):");
            String isAdminInput = scanner.nextLine().trim().toLowerCase();
            updateValues.put("isAdmin", isAdminInput.equals("yes"));
        }
        String resultMessage = trainerService.update(updateValues);
        System.out.println("==================================================================");
        System.out.println(resultMessage);
        System.out.println("==================================================================");
        if (role.equalsIgnoreCase("trainer")) {
            showTrainerMainMenu();
        }
    }		

    public void showFreeCourseOfferMenu() {
		System.out.println("Choose an option");
        System.out.println("1. Add Free Course Offer");
        System.out.println("2. Delete Free Course Offer");
        System.out.println("3. Update Free Course Offer");
        System.out.println("4. View All Free Course Offer");
		System.out.println("5. Back to Admin Menu");
        System.out.println("Enter your choice: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                addOffer();
                break;
            case 2:
                deleteOffer();
                break;
            case 3:
                updateOffer();
                break;
            case 4:
                viewOffers();
                break;
            case 5:
                showAdminMenu();
                return;
            default:
                System.out.println("Invalid choice. Try again.");
        }
        showFreeCourseOfferMenu(); // Recursive call
    }
	
	public void viewOffers() {
		List<Map<String, Object>> allOffers = freeCourseOfferService.getAllOffers();
        System.out.println("Fetching All Free Course Offer:");
        System.out.println("--------------------------------------------------------------------------------");
        for (Map<String, Object> offer : allOffers) {
            System.out.println("Offer ID: " + offer.get("id"));
            System.out.println("Paid Course Id: " + offer.get("paidCourseId"));
            System.out.println("Free Course Id: " + offer.get("freeCourseId"));
            System.out.println("--------------------------------------------------------------------------------");
        }
        if (role.equalsIgnoreCase("trainer")) {
            showTrainerMainMenu();
        }
	}
	
	public void updateOffer() {
		Map<String, Object> updateValues = new HashMap<>();
        System.out.println("Enter Offer ID :");
        int offerId = scanner.nextInt();
        scanner.nextLine();
        updateValues.put("id", offerId);
        if (confirmUpdate("Update Paid Course Id")) {
            System.out.println("Enter new Paid Course Id:");
            updateValues.put("paidCourseId", scanner.nextInt());
			scanner.nextLine();
        }
        if (confirmUpdate("Update Free Course Id")) {
            System.out.println("Enter new Free Course ID:");
            updateValues.put("freeCourseId", scanner.nextInt());
			scanner.nextLine();
        }

        String resultMessage = freeCourseOfferService.update(updateValues);
        System.out.println("==================================================================");
        System.out.println(resultMessage);
        System.out.println("==================================================================");
	}
	
	public void deleteOffer() {
		Map<String, Object> deleteValues = new HashMap<>();
        System.out.println("Enter Free Course Offer ID : ");
        deleteValues.put("offerId", scanner.nextInt());
        scanner.nextLine();
        boolean isDeleted = freeCourseOfferService.delete(deleteValues);
        if (isDeleted) {
            System.out.println("============================================");
            System.out.println("Free Course Offer Deleted Successfully...");
            System.out.println("============================================");
        } else {
        System.out.println("=============================================================================");
        System.out.println("Unable to delete offer.Please check if the offer ID is correct and not null");
        System.out.println("=============================================================================");
        }
	}
	
	public void addOffer() {
		Map<String, Object> addValues = new HashMap<>();

        System.out.println("Enter Paid Course ID: ");
        addValues.put("paidCourseId", scanner.nextInt());
        scanner.nextLine();
        System.out.println("Enter Free Course ID: ");
        addValues.put("freeCourseId", scanner.nextInt());
        scanner.nextLine();
		
        String resultMessage = freeCourseOfferService.add(addValues);
        System.out.println("==================================================================");
        System.out.println(resultMessage);
        System.out.println("==================================================================");
	}

    public void showEnrollmentMenu() {
        System.out.println("Choose an option");
        System.out.println("1. Add Enrollment");
        System.out.println("2. Delete Enrollment");
        System.out.println("3. Update Enrollment");
        System.out.println("4. View All Enrollments");
		System.out.println("5. Back to Previous Menu");
        System.out.println("Enter your choice: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                addEnrollment();
                break;
            case 2:
                deleteEnrollment();
                break;
            case 3:
                updateEnrollment();
                break;
            case 4:
                viewEnrollments();
                break;
            case 5:
                if (role.equalsIgnoreCase("admin")) {
                    showAdminMenu();
                } else if (role.equalsIgnoreCase("trainer")) {
                    showTrainerMainMenu();
                }
                return;
            default:
                System.out.println("Invalid choice. Try again.");
        }
        showEnrollmentMenu(); // Recursive call
    }
	public void viewEnrollments() {
		List<Map<String, Object>> allEnrollments = enrollmentService.getAllEnrollments();
        System.out.println("Fetching All Enrollments:");
        System.out.println("--------------------------------------------------------------------------------");
        for (Map<String, Object> enrollment : allEnrollments) {
            System.out.println("Enrollment ID: " + enrollment.get("id"));
            System.out.println("Student ID: " + enrollment.get("studentId"));
            System.out.println("Course ID: " + enrollment.get("courseId"));
            System.out.println("Batch ID: " + enrollment.get("batchId"));
            System.out.println("--------------------------------------------------------------------------------");
        }
	}
	public void updateEnrollment() {
		Map<String, Object> updateValues = new HashMap<>();
        System.out.println("Enter Enrollment ID :");
        int enrollmentId = scanner.nextInt();
        scanner.nextLine();
        updateValues.put("id", enrollmentId);

        if (confirmUpdate("Update Student")) {
            System.out.println("Enter new Student ID:");
            updateValues.put("studentId", scanner.nextInt());
			scanner.nextLine();
        }
        if (confirmUpdate("Update Course")) {
            System.out.println("Enter new Course ID:");
            updateValues.put("courseId", scanner.nextInt());
			scanner.nextLine();
        }
        if (confirmUpdate("Update Batch")) {
            System.out.println("Enter new Batch ID:");
            updateValues.put("batchId", scanner.nextInt());
			scanner.nextLine();
        }

        System.out.println("==================================================================");
        System.out.println(enrollmentService.update(updateValues));
        System.out.println("==================================================================");	
	}
	
	public void deleteEnrollment() {
		Map<String, Object> deleteValues = new HashMap<>();
        System.out.println("Enter Enrollment ID : ");
        deleteValues.put("enrollmentId", scanner.nextInt());
        scanner.nextLine();
        boolean isDeleted = enrollmentService.delete(deleteValues);
        if (isDeleted) {
            System.out.println("============================================");
            System.out.println("Enrollment Deleted Successfully...");
            System.out.println("============================================");
        } else {
            System.out.println("===================================================================================");
            System.out.println("Unable to delete enrollment.Please check if the enrollment ID is correct and not null");
            System.out.println("===================================================================================");
        }
	}
    public void addEnrollment() {
        Map<String, Object> enrollmentDetails = new HashMap<>();
        System.out.print("Enter Student ID: ");
        int studentId = scanner.nextInt();
        enrollmentDetails.put("studentId", studentId);
        scanner.nextLine();

        viewCourses();
        System.out.print("Enter the Paid Course ID from the list: ");
        int courseId = scanner.nextInt();
        enrollmentDetails.put("courseId", courseId);
        scanner.nextLine();
        System.out.println("Fetching Available Batches for Course ID: " + courseId);
        List<Map<String, Object>> courseBatches = enrollmentService.getBatchesByCourse(courseId);
        displayBatches(courseBatches);
        if (courseBatches.isEmpty()) showEnrollmentMenu();
        System.out.print("Enter Batch ID from the list: ");
        enrollmentDetails.put("batchId", scanner.nextInt());
        scanner.nextLine();
        System.out.println("==================================================================");
        System.out.println(enrollmentService.enrollStudent(enrollmentDetails));
        System.out.println("==================================================================");

        List<Map<String, Object>> freeCourses = enrollmentService.getFreeCoursesForPaidCourse(courseId);
        if (!freeCourses.isEmpty() && confirmUpdate("enroll in a free course?")) {
            System.out.print("Enter Free Course ID from the list: ");
            int freeCourseId = scanner.nextInt();
            scanner.nextLine();

            // Fetch and display available batches for the free course
            List<Map<String, Object>> freeCourseBatches = enrollmentService.getBatchesByCourse(freeCourseId);
            displayBatches(freeCourseBatches);
            if (freeCourseBatches.isEmpty()) showEnrollmentMenu();

            System.out.print("Enter Batch ID for the Free Course: ");
            int freeBatchId = scanner.nextInt();
            scanner.nextLine();

            Map<String, Object> freeEnrollmentDetails = new HashMap<>();
            freeEnrollmentDetails.put("studentId", studentId);
            freeEnrollmentDetails.put("courseId", freeCourseId);
            freeEnrollmentDetails.put("batchId", freeBatchId);
            System.out.println("==================================================================");
            System.out.println(enrollmentService.enrollStudent(freeEnrollmentDetails));
            System.out.println("==================================================================");
        }
    }
    public void displayBatches(List<Map<String, Object>> batches) {
        if (batches.isEmpty()) {
            System.out.println("------------------------------------------------------");
            System.out.println("No available batches for the selected course.");
            System.out.println("------------------------------------------------------");
        } else {
            for (Map<String, Object> batch : batches) {
                System.out.println("Batch ID: " + batch.get("batchId"));
                System.out.println("Batch Name: " + batch.get("batchName"));
                System.out.println("Start Date: " + batch.get("startDate"));
                System.out.println("End Date: " + batch.get("endDate"));
                System.out.println("Class Mode: " + batch.get("classMode"));
                System.out.println("------------------------------------------------------");
            }
        }
    }
    public void showStudentMenu() {
        System.out.println("Choose an option");
        System.out.println("1. Add Student");
        System.out.println("2. Delete Student");
        System.out.println("3. Update Student");
        System.out.println("4. View All Students");
		System.out.println("5. Back to Previous Menu");
        System.out.println("Enter your choice: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                addStudent();
                break;
            case 2:
                deleteStudent();
                break;
            case 3:
                updateStudent();
                break;
            case 4:
                viewStudents();
                break;
            case 5:
                if (role.equalsIgnoreCase("admin")) {
                    showAdminMenu();
                } else if (role.equalsIgnoreCase("trainer")) {
                    showTrainerMainMenu();
                }
                return;
            default:
                System.out.println("Invalid choice. Try again.");
        }
        showStudentMenu(); // Recursive call
    }

    public void viewStudents() {
        List<Map<String, Object>> allStudents = studentService.getAllStudent();
        System.out.println("Fetching All Students:");
        System.out.println("--------------------------------------------------------------------------------");
        for (Map<String, Object> student : allStudents) {
            System.out.println("Student ID: " + student.get("studentId"));
            System.out.println("Student Name: " + student.get("studentName"));
            System.out.println("Gender: " + student.get("gender"));
            System.out.println("Date Of Birth: " + student.get("dob"));
            System.out.println("--------------------------------------------------------------------------------");
        }
    }

    public void updateStudent() {
        Map<String, Object> updateValues = new HashMap<>();
        System.out.println("Enter Student ID :");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        updateValues.put("id", studentId);

        if (confirmUpdate("Update Student Name")) {
            System.out.println("Enter new Student Name:");
            updateValues.put("name", scanner.nextLine());
        }
        if (confirmUpdate("Update Gender (Male/Female/Prefer not to say)")) {
            System.out.println("Enter new Gender:");
            updateValues.put("gender", scanner.nextLine());
        }
        if (confirmUpdate("Update Date Of Birth (YYYY-MM-DD)")) {
            System.out.println("Enter new Date Of Birth:");
            updateValues.put("dateOfBirth", scanner.nextLine());
        }
        if (confirmUpdate("Update Email")) {
            System.out.println("Enter new Email:");
            updateValues.put("email", scanner.nextLine());
        }
        if (confirmUpdate("Update Phone Number")) {
            System.out.println("Enter new Phone Number:");
            updateValues.put("phone", scanner.nextLine());
        }

        System.out.println("==================================================================");
        System.out.println(studentService.update(updateValues));
        System.out.println("==================================================================");
    }

    public void deleteStudent() {
        Map<String, Object> deleteValues = new HashMap<>();
        System.out.println("Enter Student ID : ");
        deleteValues.put("studentId", scanner.nextInt());
        scanner.nextLine();
        boolean isDeleted = studentService.delete(deleteValues);
        if (isDeleted) {
            System.out.println("============================================");
            System.out.println("Student Deleted Successfully...");
            System.out.println("============================================");
        } else {
            System.out.println("===============================================================================");
            System.out.println("Unable to delete student.Please check if the student ID is correct and not null");
            System.out.println("===============================================================================");
        }
    }

    public void addStudent() {
        Map<String, Object> addValues = new HashMap<>();

        System.out.println("Enter Student Name: ");
        addValues.put("studentName", scanner.nextLine());
        System.out.println("Enter Gender (Male/Female/Prefer not to say): ");
        addValues.put("gender", scanner.nextLine());
        System.out.println("Enter Date of Birth (YYYY-MM-DD): ");
        addValues.put("dob", scanner.nextLine());
        System.out.println("Enter Email: ");
        addValues.put("email", scanner.nextLine());
        System.out.println("Enter Phone Number: ");
        addValues.put("phone", scanner.nextLine());

        System.out.println("==================================================================");
        System.out.println(studentService.add(addValues));
        System.out.println("==================================================================");
    }

    public void showTopicMenu() {
        System.out.println("\n Topic Management Menu");
        System.out.println("1. Add Topic");
        System.out.println("2. Delete Topic");
        System.out.println("3. Update Topic");
        System.out.println("4. View All Topics");
        System.out.println("5. Back to Admin Menu");
        System.out.print("Enter your choice: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                addTopic();
                break;
            case 2:
                deleteTopic();
                break;
            case 3:
                updateTopic();
                break;
            case 4:
                viewTopics();
                break;
            case 5:
                showAdminMenu();
                return;
            default:
                System.out.println("Invalid choice. Try again.");
        }
        showTopicMenu(); // Recursive call
    }

    private void viewTopics() {
        List<Map<String, Object>> allTopics = topicService.getAllTopics();
        System.out.println("\nFetching All Topics:");
        for (Map<String, Object> topic : allTopics) {
            System.out.println("------------------------------------------------");
            System.out.println("Topic ID: " + topic.get("topicId"));
            System.out.println("Topic Name: " + topic.get("topicName"));
            System.out.println("Course ID: " + topic.get("courseId"));
        }
        System.out.println("------------------------------------------------");
        if (role.equalsIgnoreCase("trainer")) {
            showTrainerMainMenu();
        }
    }

    private void updateTopic() {
        Map<String, Object> updateValues = new HashMap<>();
        System.out.print("Enter Topic ID to update: ");
        int topicId = Integer.parseInt(scanner.nextLine().trim());
        updateValues.put("id", topicId);

        if (confirmUpdate("Update Topic Name")) {
            System.out.print("Enter new Topic Name: ");
            updateValues.put("name", scanner.nextLine().trim());
        }
        if (confirmUpdate("Update Course ID")) {
            System.out.print("Enter new Course ID: ");
            updateValues.put("courseId", Integer.parseInt(scanner.nextLine().trim()));
        }
        System.out.println("==================================================================");
        System.out.println(topicService.update(updateValues));
        System.out.println("==================================================================");
    }

    public void deleteTopic() {
        Map<String, Object> deleteValues = new HashMap<>();
        System.out.println("Enter Topic ID : ");
        deleteValues.put("topicId", scanner.nextInt());
        scanner.nextLine();
        boolean isDeleted = topicService.delete(deleteValues);
        if (isDeleted) {
            System.out.println("============================================");
            System.out.println("Topic Deleted Successfully...");
            System.out.println("============================================");
        } else {
            System.out.println("=============================================================================");
            System.out.println("Unable to delete topic.Please check if the topic ID is correct and not null");
            System.out.println("=============================================================================");
        }
    }

    public void addTopic() {
        Map<String, Object> addValues = new HashMap<>();
        System.out.println("Enter Topic Name: ");
        addValues.put("topicName", scanner.nextLine());
        System.out.println("Enter Course ID: ");
        addValues.put("courseId", scanner.nextInt());
        scanner.nextLine();
        boolean isCreated = topicService.add(addValues);
        if (isCreated) {
            System.out.println("============================================");
            System.out.println("New Topic Added Successfully...");
            System.out.println("============================================");
        } else {
            System.out.println("============================================");
            System.out.println("Error: topicName or courseId cannot be null.");
            System.out.println("============================================");
        }
    }

    public void showCourseMenu() {
        System.out.println("\nCourse Menu - Choose an option:");
        System.out.println("1. Add Course");
        System.out.println("2. Delete Course");
        System.out.println("3. Update Course");
        System.out.println("4. View All Courses");
        System.out.println("5. Back to Previous Menu");
        System.out.print("Enter your choice: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                addCourse();
                break;
            case 2:
                deleteCourse();
                break;
            case 3:
                updateCourse();
                break;
            case 4:
                viewCourses();
                break;
            case 5:
                showAdminMenu();
                return;
            default:
                System.out.println("Invalid choice. Try again.");
        }
        showCourseMenu(); // Recursive call
    }

    public void addCourse() {
        Map<String, Object> courseDetails = new HashMap<>();
        System.out.print("Enter Course Name: ");
        courseDetails.put("courseName", scanner.nextLine().trim());

        System.out.print("Is the course paid? (true/false): ");
        boolean isPaid = scanner.nextBoolean();
        scanner.nextLine();
        courseDetails.put("isPaid", isPaid);

        if (isPaid) {
            System.out.print("Enter Course Fee in numbers: ");
            courseDetails.put("courseFee", scanner.nextInt());
            scanner.nextLine();
        } else {
            courseDetails.put("courseFee", 0);
        }

        System.out.print("Enter Course Description: ");
        courseDetails.put("courseDescription", scanner.nextLine());

        System.out.println("==================================================================");
        System.out.println(courseService.addCourse(courseDetails));
        System.out.println("==================================================================");
    }

    public void deleteCourse() {
        System.out.print("Enter Course ID to delete: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();

        Map<String, Object> deleteValues = new HashMap<>();
        deleteValues.put("courseId", courseId);

        if (courseService.deleteCourse(deleteValues)) {
            System.out.println("============================================");
            System.out.println("Course Deleted Successfully...");
            System.out.println("============================================");
        } else {
            System.out.println("=============================================================================");
            System.out.println("Unable to delete course. Please check if the course ID is correct and not null.");
            System.out.println("=============================================================================");
        }
    }

    public void updateCourse() {
        Map<String, Object> updateValues = new HashMap<>();
        System.out.print("Enter Course ID to update: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        updateValues.put("id", courseId);

        if (confirmUpdate("Update Course Name")) {
            System.out.print("Enter new Course Name: ");
            updateValues.put("name", scanner.nextLine());
        }
        if (confirmUpdate("Update Paid/Free status")) {
            System.out.print("Is the course now paid? (true/false): ");
            boolean isPaid = scanner.nextBoolean();
            updateValues.put("isPaid", isPaid);
            scanner.nextLine();

            if (isPaid && confirmUpdate("Update Course Fee")) {
                System.out.print("Enter new Course Fee: ");
                updateValues.put("fee", scanner.nextInt());
                scanner.nextLine();
            } else {
                updateValues.put("fee", 0);
            }
        }
        if (confirmUpdate("Update Description")) {
            System.out.print("Enter new Description: ");
            updateValues.put("description", scanner.nextLine());
        }
        System.out.println("==================================================================");
        System.out.println(courseService.updateCourse(updateValues));
        System.out.println("==================================================================");
    }
    public void viewCourses() {
        System.out.println("Fetching All Paid Courses and Their Associated Free Courses:");
        System.out.println("--------------------------------------------------------------------------------");

        List<Map<String, Object>> allCourses = enrollmentService.getAllPaidCourses();

        for (Map<String, Object> course : allCourses) {
            displayCourseDetails(course);

            List<Map<String, Object>> freeCourses = enrollmentService.getFreeCoursesForPaidCourse((int) course.get("courseId"));
            displayFreeCourses(freeCourses);
        }

        if (role.equalsIgnoreCase("trainer")) {
            showTrainerMainMenu();
        }
    }
    public void displayCourseDetails(Map<String, Object> course) {
        System.out.println("Course ID: " + course.get("courseId"));
        System.out.println("Course Name: " + course.get("courseName"));
        System.out.println("Course Fee: " + course.getOrDefault("courseFee", "N/A"));
        System.out.println("Description: " + course.get("description"));
        System.out.println("------------------------------------------------------");
    }

    public void displayFreeCourses(List<Map<String, Object>> freeCourses) {
        if (freeCourses.isEmpty()) {
            System.out.println("No free courses available with this course.");
        } else {
            System.out.println("Free Courses Available:");
            for (Map<String, Object> freeCourse : freeCourses) {
                displayCourseDetails(freeCourse);
            }
        }
        System.out.println("------------------------------------------------------");
    }
    public boolean confirmUpdate(String field) {
        System.out.print("Do you want to " + field + "? (yes/no): ");
        return scanner.nextLine().equalsIgnoreCase("yes");
    }
}