// Kavya Rammohan
//List.java

public class List{

   // Define class Node
   private class Node {
        Object data;
        Node next;
        Node prev;

        Node(Object data){
          this.data = data;
          next = null;
          prev = null;
        }

        Node(Object d, Node p, Node n){
          data = d;
          prev = p;
          next = n;
        }

        public String toString(){
          return "" +data;
        }
    }

   // Define variables
   private Node front;
   private Node back;
   private Node cursor;
   private int numItems;
   private int index;

   // Creates a new empty list
   List(){
        front = null;
        back = null;
        cursor = null;
        index = -1;
        numItems = 0;
   }

  // Returns number of elements in this list
  int length(){
        return numItems;
  }

  // Returns the index of the cursor element in this list, or returns -1
  // if the cursor element is undefined
  int getIndex(){
        int index=-1;
        if(cursor==null){
          return index;
        }
        for(Node N=front; N!=null;N=N.next){
          index++;
          if(N==cursor) break;
        }
        return index;
  }

  // Returns front element in this list. Pre: length() > 0
  Object front(){
        if(numItems == 0 ){
          throw new RuntimeException("List Error: front() called on empty List");
        }
        if(length()>0){
          return front.data;
        }
        return null;
  }

  // Returns back element in this list. Pre: length()>0
  Object back(){
        if(length() == 0){
          throw new RuntimeException("List Error: back() called on empty List");
        }
        if(length()>0){
          return back.data;
        }
        return null;
  }

  // Returns cursor element in this list. Pre: length()>0, getIndex()>=0
  Object getElement(){
        if(length() == 0 || getIndex()<0){
          throw new RuntimeException("List Error: getElement() called on empty List");
        }
        if(length()>0 && getIndex()>=0){
        return cursor.data;
        }
 }
        return null;
  }

  // Returns cursor element in this list. Pre: length()>0, getIndex()>=0
  Object getElement(){
        if(length() == 0 || getIndex()<0){
          throw new RuntimeException("List Error: getElement() called on empty List");
        }
        if(length()>0 && getIndex()>=0){
        return cursor.data;
        }
        return null;
  }

  // Returns true if this List and L are the same integer sequence. The
  // cursor is ignored in both lists.
 public boolean equals(Object L){
        if (L == null || !(L instanceof List)) {
            return false;
        }
        Node x = front;
        Node y = ((List)L).front;
        while (x != null && y != null){
            if (!(x.data.equals(y.data))){
                return false;
            }
            x = x.next;
            y = y.next;
        }
        if (x != null || y != null) return false;
        return true;
    }

  // Manipulation procedures


  // Re-sets this List to the empty state
  void clear(){
        front = null;
        back = null;
        cursor = null;
        numItems = 0;
  }

  // If 0<=i<=length()-1, moves the cursor to the element at index i,
  // otherwise the cursor becomes undefined
  void moveTo(int i){
        if(i>=0 && i<=length()-1){
          cursor = front;
          for(; i>0; i--){
                cursor = cursor.next;
          }
        }
        else{
                cursor =null;
          }
   }

   // If 0<getIndex()<=length()-1, moves the cursor one step toward the
   // front of the list. If getIndex()==0, cursor becomes undefined. 
   // If getIndex()==-1, cursor remains undefined. This operation is
   // equivalent to moveTo(getIndex()-1).
   //
   void movePrev(){
        if(0<getIndex() && getIndex()<=(length()-1)){
          cursor = cursor.prev;
        }
        else{
          cursor = null;
        }
   }

   // If 0<=getIndex()<length()-1, moves the cursor one step toward the
   // back of the list. If getIndex()==length()-1, cursor becomes
   // undefined. If index==-1, cursor remains undefined. This
   // operation is equivalent to moveTo(getIndex()+1).
   //
   void moveNext(){
        if(0<=getIndex() && getIndex()<(length()-1)){
          cursor = cursor.next;
          index++;
        }
        else{
          cursor = null;
        }

   }
//Inserts new element before front element in this List.
   //
   void prepend(Object data){
        Node N = new Node(data, null, front);
        numItems++;
        if(front!=null){
             front.prev = N;
        }
        else{
             back = N;
        }
        front = N;
   }

   // Inserts new element after back element in this List.
   //
   void append(Object data){
        Node N = new Node(data, back, null);
        numItems++;
        if(back!=null){
             back.next = N;
        }
        else{
             front = N;
        }
        back = N;
   }

  // Inserts new element before cursor element in this
  // List. Pre: length()>0, getIndex()>=0
  //
   void insertBefore(Object data){
        if(!(length()>0 && getIndex()>=0)){
                return;
        }
        numItems++;
        Node N = new Node(data, cursor.prev, cursor);
        if(cursor.prev!=null){
           cursor.prev.next=N;
        }
        else{
           front = N;
        }

   }

   // Inserts new element after cursor element in this 
   // List. Pre: length()>0, getIndex()>=0
   //
   void insertAfter(Object data){
        if(!(length()>0 && getIndex()>=0)){
          return;
        }
        numItems++;
        Node N = new Node(data, cursor, cursor.next);
        if(cursor.next!=null){
            cursor.next.prev = N;
        }
        else{
            back = N;
        }
        cursor.next=N;
   }

 // Deletes the front element in this List. Pre: length()>0   
   void deleteFront(){
        if(length() == 0){
            throw new RuntimeException("List Error: deleteFront() called on empty list.");
        }
        if(length() >0){
            numItems--;
        }
        if(cursor==front){
            cursor = null;
        }
            front = front.next;
            front.prev = null;
        }


   // Deletes the back element in this List. Pre: length()>0
   //
   void deleteBack(){
        if(length()==0){
            throw new RuntimeException("List Error: deleteBack() called on empty list.");
        }
        if(length() >0){
            numItems--;
        }
        if(cursor==back){
            cursor = null;
        }
            back = back.prev;
            back.next = null;

        }


   //  Deletes cursor element in this List. Cursor is undefined after this 
   //  operation. Pre: length()>0, getIndex()>=0
   //
   void delete(){
        if(length()==0){
            throw new RuntimeException("List Error: delete() called on empty list.");
        }
        if(getIndex()<0){
            throw new RuntimeException("List Error: cursor is not defined.");
        }
        if(cursor==front){
           deleteFront();
        }
        else if(cursor==back){
           deleteBack();
        }
        if(length()>0 && getIndex()>0){
           numItems--;
           cursor.next.prev=cursor.prev;
           cursor.prev.next=cursor.next;
           cursor=null;
        }
   }

   //  Overides Object's toString method. Creates a string 
   //  consisting of a space separated sequence of integers
   //  with front on the left and back on the right.
   //
   public String toString(){
        String s = "";
        for(Node N = front; N!=null; N=N.next){
            s += N.toString() + " ";
        }
        return s;
   }

}
