import java.util.List;

import com.sitepk.qq.Group;
import com.sitepk.qq.WebQQClient;


public class test {
	public static void main(String[] args) throws Exception {
		WebQQClient user = new WebQQClient();
		user.setId("1919606673");
		user.setPassword("");
		user.login();
		//user.getAllFriends();
		//user.sendMsg("63090585", "测试中文消息");
		//user.sendMsg("2608667081", "测试中文消息");
		//
		List<Group> groups = user.getAllGroups();
		
		for(Group group: groups){
			user.sendGroupMsg(group.getGid(), "赶快访问【微博兔】吧!");
		}
	}
}
