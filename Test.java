package proj1;

public class Test {

	public static void main(String[] args){
		
		
		SinglyLinkedList s = new SinglyLinkedList();
		s.addAtStart(0);
		s.addAtEnd(1);
		s.addAtEnd(2);
		s.addAtEnd(3);
		s.print();
		SinglyLinkedList t = s.split();
		System.out.println("AFter Split:");
		t.print();
		s.print();
		
	}
}
