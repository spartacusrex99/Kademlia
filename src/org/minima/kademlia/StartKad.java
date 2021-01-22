package org.minima.kademlia;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.List;

import kademlia.JKademliaNode;
import kademlia.dht.GetParameter;
import kademlia.dht.JKademliaStorageEntry;
import kademlia.dht.KadContent;
import kademlia.node.KademliaId;
import kademlia.node.Node;
import kademlia.simulations.DHTContentImpl;

public class StartKad {

	public static void main(String[] zArgs) {
		System.out.println("Kademilia Test Net");
	
	
		try {
			String BootID = "ASF45678947584567463";
			
			//BOOTSTRAPPER
			JKademliaNode kad1 = new JKademliaNode("OwnerName1", new KademliaId(BootID), 12049);
			
			//NEW NODE
			JKademliaNode kad2 = new JKademliaNode("OwnerName2", new KademliaId(), 12057);
			kad2.bootstrap(kad1.getNode());
			DHTContentImpl c = new DHTContentImpl(kad2.getOwnerId(), "some data");
			kad2.put(c);
			KademliaId objkey = c.getKey();
//			System.out.println("KAD2 KEY  : "+objkey);
			
			
//			GetParameter gp = new GetParameter(objkey, DHTContentImpl.TYPE);
//			JKademliaStorageEntry entry = kad1.get(gp);
//			String data = new String(entry.getContent());
//			System.out.println("entry : "+data);
			

			//Thread.sleep(1000);
			
//			System.out.println("kad1 : "+kad1);
//			System.out.println("kad2 : "+kad1);
			
			
			//NEW NODE
			JKademliaNode kad3 = new JKademliaNode("OwnerName3", new KademliaId(), 12063);
			Node boots = new Node(new KademliaId(BootID), InetAddress.getByName("127.0.0.1"), 12049);
			kad3.bootstrap(boots);
			
			//Get a list of contacts..
			System.out.println("kad3 : "+kad3);
			
			
			GetParameter gp = new GetParameter(objkey, DHTContentImpl.TYPE);
			JKademliaStorageEntry entry = kad3.get(gp);
			String data = new String(entry.getContent());
			System.out.println("entry : "+data);
			
			
			
			
			//And shut down..
			Thread.sleep(2000);
			kad1.shutdown(false);
			kad2.shutdown(false);
			kad3.shutdown(false);

			System.out.println("Shutdown complete");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
