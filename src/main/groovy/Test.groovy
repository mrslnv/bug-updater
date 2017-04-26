/**
 * Created by mirek on 5/29/2015.
 */
class Test {
    long id
    String status

    Test(Long id, String status) {
        this.id = id.longValue()
        this.status = status
    }

    def updateStatus(String newStatus){
        if ("Failed".equals(newStatus))
            status = newStatus
        else if ("Skipped".equals(newStatus) && !"Failed".equals(status)){
            status = newStatus
        }
    }
}
