package pl.estrix.warehouse.controller.list;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import javax.annotation.PostConstruct;

public class Service {

//    private DataClient dataClient;
//
//    private UserClient userClient;

    /**
     * Every list stored under the same application on Gluon CloudLink has a unique id:
     */
    private static final String CLOUD_LIST_ID = "comments v2.0";

    /**
     * An observable wrapper of the retrieved list, used to expose it and bind the
     * ListView items to the list.
     */
    private final ListProperty<CompanyModel> commentsList =
            new SimpleListProperty<>(FXCollections.<CompanyModel>observableArrayList());

    /**
     * The authenticated user. Check
     * http://docs.gluonhq.com/cloudlink/latest/#_applying_login_methods
     */
//    private final ObjectProperty<User> user = new SimpleObjectProperty<>();

    /**
     * Contains a comment that can be edited
     */
    private final ObjectProperty<CompanyModel> activeComment = new SimpleObjectProperty<>();

    public ObjectProperty<CompanyModel> activeCommentProperty() {
        return activeComment;
    }

    /**
     * Cache to manage avatar images
     */
//    private static final Cache<String, Image> CACHE;

    static {
//        CACHE = Services.get(CacheService.class)
//                .map(cache -> cache.<String, Image>getCache("images"))
//                .orElseThrow(() -> new RuntimeException("No CacheService available"));
    }

//    public static Image getUserImage(String userPicture) {
//
//
//        System.out.println("Service.userPicture: " + userPicture);
//
//        if (userPicture == null || userPicture.isEmpty()) {
//            /*
//             * https://commons.wikimedia.org/wiki/File:WikiFont_uniE600_-_userAvatar_-_blue.svg
//             * By User:MGalloway (WMF) (mw:Design/WikiFont) [CC BY-SA 3.0 (http://creativecommons.org/licenses/by-sa/3.0)], via Wikimedia Commons
//             */
//            userPicture = Service.class.getResource("/images/avatar.png").toExternalForm();
//        }
//        userPicture = Service.class.getResource("/images/avatar.png").toExternalForm();
//        // try to retrieve image from cache
////        Image answer = CACHE.get(userPicture);
////        if (answer == null) {
////            // if not available yet, create new image from URL
////            answer = new Image(userPicture, true);
////            // store it in cache
////            CACHE.put(userPicture, answer);
////        }
////        return answer;
//        return new Image(userPicture, true);
//    }

    /**
     * See Afterburner.fx
     */
    @PostConstruct
    public void postConstruct() {
//        userClient = new UserClient();
//        dataClient = DataClientBuilder.create()
//                .authenticateWith(userClient)
//                .build();
//        user.bind(userClient.authenticatedUserProperty());

    }

    /**
     * Once there's a valid dataClient, the contents of the list can be retrieved. This will return a
     * GluonObservableList. Note the flags:
     * - LIST_WRITE_THROUGH: Changes in the local list are reflected to the remote copy of that list on Gluon CloudLink.
     * - LIST_READ_THROUGH: Changes in the remote list on Gluon CloudLink are reflected to the local copy of that list
     * - OBJECT_READ_THROUGH: Changes in observable properties of objects in the remote list on Gluon CloudLink are reflected to the local objects of that list
     * - OBJECT_WRITE_THROUGH: Changes in the observable properties of objects in the local list are reflected to the remote copy on Gluon CloudLink
     *
     * This means that any change done in any client app will be reflected in Gluon CloudLink, and immediately broadcast
     * to all the listening applications.
     */
    public void retrieveComments() {

//        User usr =new User();
//        usr.setNetworkId("networkId2");
//        user.set(usr);
//
//        System.out.println("Service.retrieveComments: " );

//        GluonObservableList<Comment> retrieveList = DataProvider.retrieveList(
//                dataClient.createListDataReader(CLOUD_LIST_ID,
//                        Comment.class,
//                        SyncFlag.LIST_READ_THROUGH, SyncFlag.LIST_WRITE_THROUGH,
//                        SyncFlag.OBJECT_READ_THROUGH, SyncFlag.OBJECT_WRITE_THROUGH));

//        retrieveList.stateProperty().addListener((obs, ov, nv) -> {
//            if (ConnectState.SUCCEEDED.equals(nv)) {
//                commentsList.set(retrieveList);
//            }
//        });

//        Comment(String author, String content, String imageUrl, String networkId)
        addComment(new CompanyModel("author 1", "content1", "networkId1"));
        addComment(new CompanyModel("author 2", "content2", "networkId2"));
        addComment(new CompanyModel("author 3", "content3", "networkId3"));
        addComment(new CompanyModel("author 4", "content4", "networkId4"));
        addComment(new CompanyModel("author 5", "content5", "networkId5"));
        addComment(new CompanyModel("author 6", "content6", "networkId6"));
        addComment(new CompanyModel("author 7", "content7", "networkId7"));
        addComment(new CompanyModel("author 8", "content8", "networkId8"));
        addComment(new CompanyModel("author 9", "content9", "networkId9"));
        addComment(new CompanyModel("author 0", "content0", "networkId0"));
        addComment(new CompanyModel("author a", "contenta", "networkIda"));
        addComment(new CompanyModel("author b", "contentb", "networkIdb"));
        addComment(new CompanyModel("author c", "content8", "networkIdc"));
        addComment(new CompanyModel("author d", "content9", "networkIdd"));
        addComment(new CompanyModel("author e", "content0", "networkIde"));
        addComment(new CompanyModel("author f", "contenta", "networkIdf"));
        addComment(new CompanyModel("author g", "contentb", "networkIdg"));
        addComment(new CompanyModel("author h", "content9", "networkIdh"));
        addComment(new CompanyModel("author i", "content0", "networkIdi"));
        addComment(new CompanyModel("author j", "contenta", "networkIdj"));
        addComment(new CompanyModel("author k", "contentb", "networkIdk"));
    }

    /**
     * Add a new comment to the list
     * Note comments can be deleted directly on the ListView, since it's bound to the list
     * @param comment
     */
    public void addComment(CompanyModel comment) {
        commentsList.get().add(comment);
    }

    /**
     *
     * @return a ListProperty, the wrapper of the remote list of comments.
     */
    public ListProperty<CompanyModel> commentsProperty() {
        return commentsList;
    }

//    public User getUser() {
//        User usr =new User();
//        usr.setNetworkId("networkId2");
//        return usr;
////        return user.get();
//    }
//
//    public ObjectProperty<User> userProperty() {
//        return user;
//    }
}