//Kavya Rammohan
//Matrix.java

class Matrix{
//create Entry for Matrix
	private class Entry{
	  int col;
	  double val;
	  
	  Entry(int col, double val){
		this.col = col;
		this.val = val;
	  }
	
//String toString method declaration
	  public String toString(){
	    return "("+col+", "+val+")";
	  }

//equals() method declaration
	  public boolean equals(Object O){
	    if(O == null){
		return false;
	    }
		return((Entry)O).col == col && ((Entry)O).val == val;
	  }
	}
	
	private List[] M;
	private int NNZ;

//Makes a new nxn zero Matrix. pre: n>=1
	public Matrix(int i){
	  if(1>i){
	 	return;
	  }
	  M = new List[i];
	  int j;
	  for(j=0;j<i;j++){
	    M[j]=new List();
	    NNZ = 0;
  	  }
	}

//Returns n, the number of rows and columns of this Matrix
	public int getSize(){
	  return M.length;
	}

//Returns the number of non-zero entries in this Matrix
	public int getNNZ(){
	  return NNZ;
	}

//Overrides Object's equals() method
	public boolean equals(Object x){
	  if (x==null){
		System.out.println("Matrix Error: Invalid input");
		return false;
	  }
	  Matrix A = (Matrix)x;
	  if(A.getSize() != M.length){
		  return false;
          }
          for(int a=0; a<M.length; a++){
		if(!(M[a].equals(A.M[a]))){
			return false;
		}
 	  }
	  return true;
	}
	 
//sets this Matrix to the zero state
	void makeZero(){
          for(int i=0; i<M.length;i++){
                M[i].clear();
          }
          NNZ = 0;
        }

	Matrix copy(){
  	  Matrix A = new Matrix(M.length);
  	  for(int i=0; i<M.length; i++){
	    List X = M[i];
	    List Y = A.M[i];
	    for(X.moveTo(0); X.getIndex()>=0; X.moveNext()){
		Object Z = X.getElement();
		if(Z==null){
		   return null;
		}
		Entry E = (Entry)Z;
		Entry F = new Entry(E.col, E.val);
		Y.append(F);
	    }
 	  }
	  A.NNZ=NNZ;
	  return A;
	}


//Changes ith row, jth column of this Matrix to x
//pre: 1<=i<=getSize(), 1<=j<=getSize()
	void changeEntry(int i, int j, double x){
	  if((i>M.length) || (i<1)){
		throw new RuntimeException("Matrix Error: Row numbers not the same");
  }
	  else if((j>M.length) || (j<1)){
		throw new RuntimeException("Matrix Error: Column numbers not the same");
	  }
	
	  Entry A = null; 
	  for(M[i-1].moveTo(0); M[i-1].getIndex()>=0; M[i-1].moveNext()){
		Object O = M[i-1].getElement();
		if(O==null){
		  return;
		}
		A = (Entry)O;
		if(A.col<j){
		  continue;
		}
		break;
	    }

	  if(M[i-1].getIndex()==-1){
	    if(x!=0){
	      M[i-1].append(new Entry(j, x));
	      NNZ++;
	    }
	  }
	  else if(A!=null && A.col==j){
	    if(x==0){
	      M[i-1].delete();
	      NNZ--;
	    }
	    else{
	      A.val=x;
	    }
	  }
	  else{
	    if(x!=0){
	      M[i-1].insertBefore(new Entry(j,x));
	      NNZ++;
	    }
	  }
	}


//Returns a new Matrix that is the scalar product of this Matrix with x
	Matrix scalarMult(double x){
	  if(x==0){
	    this.makeZero();
	  }
	  Matrix A = new Matrix(M.length);
	  if(x!=0){
	    for(int k=0;k<M.length;k++){
		List X = M[k];
		List Y = A.M[k];
		for(X.moveTo(0);X.getIndex()>=0;X.moveNext()){
		  Object N = X.getElement();
		  if(N ==null){
		  	return null;
		  }
		  Entry E = (Entry)N;
		  Entry F = new Entry(E.col, (x*(E.val)));
		  Y.append(F);
		}
	    }
	  }
	  A.NNZ = NNZ;
	  return A;
	}


//Computes vector dot product of two matrix rows represented by Lists
	private static double dot(List P, List Q){
	  double product=0;
	  P.moveTo(0);
	  Q.moveTo(0);
	  while(P.getIndex()>=0 && Q.getIndex()>=0){	
	    Entry A;
	    Entry B;
	    A = (Entry)P.getElement();
	    B = (Entry)Q.getElement();
	    if(A.col == B.col){
		product += A.val*B.val;
	  	P.moveNext();
		Q.moveNext();
	    }
	    else if(A.col<B.col){
		P.moveNext();
	    } 
	    else{
		Q.moveNext();
	    }
	  }
	  return product;
	}

	
//Returns a new Matrix that is the sum of this Matrix with N
//pre: getSize()==N.getSize()
	Matrix add(Matrix N){
	  if(M.length!=N.M.length){
		return null;
	  }
	  Matrix A = new Matrix(M.length);
	  for(int i=0; i<M.length; i++){
	    List X = M[i];
	    List Y = N.M[i];
	    List Z = A.M[i];
	    
   	    X.moveTo(0);
	    Y.moveTo(0);
	    while(X.getIndex()>=0 && Y.getIndex()>=0){
	      Entry E = (Entry)(X.getElement());
	      Entry F = (Entry)(Y.getElement());
	      A.NNZ++;
	      if(E.col == F.col){
		if(E.val+F.val!=0){
		  Z.append(new Entry(E.col, (E.val+F.val)));
		}
		else{
		  NNZ--;
		}
		if(E.val == 0){
		  Z.append(new Entry(E.col, F.val));
		}
		if(F.val == 0){
		  Z.append(new Entry(E.col, E.val));
		}
		X.moveNext();
		Y.moveNext();
	    }
	    else if(F.col > E.col){
		Z.append(new Entry(E.col, E.val));
		X.moveNext();
	    }
	    else{
		Z.append(new Entry(F.col, F.val));
		Y.moveNext();
	    }
	  }

	  if(X.getIndex()==Y.getIndex()){
	    continue;
	  }
	  List W;
	  if(X.getIndex()>=0){
	    W = X;
	  }
	  else{
	    W = Y;
	  }
	  while(W.getIndex()>=0){
	    Entry G = (Entry)(W.getElement());
	    A.NNZ++;
	    Z.append(new Entry(G.col, G.val));
	    W.moveNext();
	  }
	  }
	  return A;
	}
	


//Returns a new Matrix that is the difference of this Matrix with N
//pre: getSize()==N.getSize
	Matrix sub(Matrix N){
	  if(M.length!=N.M.length){
		return null;
	  }
          if(N==this){
            return new Matrix(M.length);
          }
          Matrix A = new Matrix(M.length);
          for(int i=0; i<M.length; i++){
            List X = M[i];
            List Y = N.M[i];
            List Z = A.M[i];

            X.moveTo(0);
            Y.moveTo(0);
            while(X.getIndex()>=0 && Y.getIndex()>=0){
              Entry E = (Entry)(X.getElement());
              Entry F = (Entry)(Y.getElement());
              A.NNZ++;
              if(E.col == F.col){
                if(E.val-F.val!=0){
                  Z.append(new Entry(E.col, (E.val-F.val)));
                }
                else{
                  NNZ--;
                }
                X.moveNext();
                Y.moveNext();
            }
            else if(E.col < F.col){
                Z.append(new Entry(E.col, E.val));
                X.moveNext();
            }
            else{
                Z.append(new Entry(F.col, 0-F.val));
                Y.moveNext();
            }
          }

          if(X.getIndex()==Y.getIndex()){
            continue;
          }
	  List W;
	  int c;
	  if(X.getIndex()>=0){
	    W = X;
	    c = 1;
	  }
	  else{
	    W = Y;
	    c = -1;
	  }
	  while(W.getIndex()>=0){
	    Entry G = (Entry)(W.getElement());
	    A.NNZ++;
	    Z.append(new Entry(G.col, (c*G.val)));
	    W.moveNext();
	  }
	 }
	 return A;
	}


//Returns a new Matrix that is the transpose of this Matrix
	Matrix transpose(){
	  Matrix N = new Matrix(M.length);
	  Entry E;
	  int i;
	  for(int j=0; j<M.length; j++){
	    List L = M[j];
	    for(L.moveTo(0);L.getIndex()>=0;L.moveNext()){
	      E=(Entry)L.getElement();
	      i=E.col-1;
	      N.M[i].append(new Entry(j+1,E.val));
	    }
	  }
	  N.NNZ=NNZ;
	  return N;
	}



//Returns a new Matrix that is the product of this Matrix with N
//pre: getSize()==N.getSize()
	Matrix mult(Matrix N){
	  if(M.length!=N.M.length){
	   	return null;
	  }
	 
	  Matrix A = N.transpose();
	  Matrix B = new Matrix(M.length);
	  if(NNZ==0 || N.NNZ==0){
	    return B;
	  }
	  for(int i=0; i<M.length;i++){
	    if(M[i].length()==0){
	      continue;
	    }
	    for(int j=0;j<M.length;j++){
		if(A.M[i].length()==0){
		  continue;
		}
		double x=dot(M[i], A.M[j]);
	        if(x!=0){
		  B.M[i].append(new Entry(j+1,x));
		  B.NNZ++;
		}
	    }
	  }
	  return B;
	}


//Overrides Object's toString() method
	public String toString(){
	  String s = "";
	  for(int i=0; i<M.length; i++){
	    if(M[i].length()>0){
	      s += (i+1)+": "+M[i];
	      s += "\n";
	    }
	  }
	  return s;
	}

}

	
 
