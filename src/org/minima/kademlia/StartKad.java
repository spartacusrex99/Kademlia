package org.minima.kademlia;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kademlia.JKademliaNode;
import kademlia.dht.GetParameter;
import kademlia.dht.JKademliaStorageEntry;
import kademlia.node.KademliaId;
import kademlia.node.Node;
import kademlia.routing.Contact;
import kademlia.simulations.DHTContentImpl;

public class StartKad {

	public static void main(String[] zArgs) {
		System.out.println("Kademilia Test Net");
	
	
		try {
			String BootID = "ASF45678947584567463";
			
			//BOOTSTRAPPER
//			JKademliaNode kad1 = new JKademliaNode("OwnerName1", new KademliaId(BootID), 12049);
			JKademliaNode kad1 = new JKademliaNode("OwnerName1", new KademliaId(), 12049);
//			Node boots = new Node(new KademliaId(BootID), InetAddress.getByName("127.0.0.1"), 12049);
			
			//NEW NODE
			JKademliaNode kad2 = new JKademliaNode("OwnerName2", new KademliaId(), 12057);
			kad2.bootstrap(kad1.getNode());
			
			//Add Data..
			DHTContentImpl c = new DHTContentImpl(kad2.getOwnerId(), "some data");
			kad2.put(c);
			KademliaId objkey = c.getKey();
			
			//NEW NODE
			Random rand = new Random();
			int nlen = 10;
			JKademliaNode[] knodes = new JKademliaNode[nlen];
			for(int i=0;i<nlen;i++) {
				JKademliaNode kadn = new JKademliaNode("Owner_"+i, new KademliaId(), 12063+i);
				
				if(rand.nextBoolean()) {
					System.out.print("New Node.. "+i+" Boot_1");
					kadn.bootstrap(kad1.getNode());
					System.out.print("..DONE\n");
				}else {
					System.out.print("New Node.. "+i+" Boot_2");
					kadn.bootstrap(kad2.getNode());
					System.out.print("..DONE\n");
				}
				
				knodes[i] = kadn;
			}
			
			//Get a list of contacts..
			JKademliaNode testnode = knodes[nlen-1];
			System.out.println("last kad : "+testnode);
			
			try {
				GetParameter gp = new GetParameter(objkey, DHTContentImpl.TYPE);
				JKademliaStorageEntry entry = testnode.get(gp);
				String data = new String(entry.getContent());
				
				JsonObject json = (JsonObject) new JsonParser().parse(data);
				System.out.println("entry : "+json.get("data"));
				
			}catch(Exception exc) {
				exc.printStackTrace();
				Thread.sleep(1000);
			}
			
			//Get a contact list..
			List<Contact> cts =  testnode.getRoutingTable().getAllContacts();
			System.out.println("KADN Contacts : "+cts.size());
			for(Contact ct : cts) {
				InetSocketAddress addr = ct.getNode().getSocketAddress();
				System.out.println("Contact : "+addr.getHostName()+":"+addr.getPort());
			}
			
			for(int i=0;i<nlen;i++) {
				System.out.println(i+") "+knodes[i].getStatistician().toString());
			}
			
			
			//And shut down..
			Thread.sleep(2000);
			kad1.shutdown(false);
			kad2.shutdown(false);
			
			for(int i=0;i<nlen;i++) {
				knodes[i].shutdown(false);
			}
			
			System.out.println("Shutdown complete");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
