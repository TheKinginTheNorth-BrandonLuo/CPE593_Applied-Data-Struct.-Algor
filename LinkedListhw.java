public class LinkedListhw {
    private static class Node { // LinkedList.Node
        int data;
        Node next;
        public Node(int v, Node n) {
            data = v;
            next = n;
        }
    };

    private Node head;
    private Node tail;
    //private int listCount;

    // public LinkedList() {
    //     head = new ListNode(null);
    //     tail = new ListNode(null);
    //     listCount = 0;
    // }
    // public LinkedList(int v) { 
    //     head = new Node(v, null); 
    // }

    // O(1)
    public void addEnd(int v) {
        Node temp=new Node(v,null);
		if(tail==null)
			head=tail=temp;
		else
			tail.next=temp;
		tail=temp;
    }

    public void insert(int n,int v) {
		Node p=head;
		for(int i=0;i<n-1;i++)
			p=p.next;
		p.next=new Node(v,p.next);
	}

    // O(1)
    public void addStart(int v) {
        Node temp=new Node(v,head);
		if(head==null)
			head=tail=temp;
		head=temp;
    }

    // O(1)
    public void removeStart() {
        if(head==null)
			return;
		if(head.next==null) {
			head=tail=null;
			return;
		}
        head = head.next;
    }

    // O(len)
    public void removeEnd() {
		if(tail==null)
			return;
		if(head.next==null) {
			head=tail=null;
			return;
		}
		Node p;
		for(p=head;p.next.next!=null;p=p.next)
			;
		p.next=null;
    }

    public String toString() {
		Node p=head; 
		StringBuilder sb=new StringBuilder();
		while(p!=null) {
			sb.append(p.data+" ");
			p=p.next;
		}
		return sb.toString();
	}
    // O(n)
    public int getLength(Node head) {
        if (head == null) {  
            return 0;  
        }  
  
        int len = 0;  
        Node cur = head;  
        while (cur != null) {  
            len++;  
            cur = cur.next;
        }  
        return len;
    }
    
    public Iterator<T> iterator() {
        if (isEmpty()) {
            return Collections.<T>emptyList().iterator();
        }
        return new Iterator<T>() {
            private Node<T> currentNode = null;
    
            public boolean hasNext() {
                return currentNode != lastNode;
            }
    
            public T next() {
                if (currentNode == null) {
                    currentNode = firstNode;
                    return currentNode.data;
                }
                if (currentNode.nextNode == null) {
                    throw new NoSuchElementException();
                }
                currentNode = currentNode.nextNode;
                return currentNode.data;
            }
        };
    }
};