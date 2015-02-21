import java.util.*;
import java.io.*;

public class VectorStack implements Stack , Serializable
{
  private Vector stack ;

  public VectorStack ()
  {
    stack = new Vector();
  }
  public boolean empty()
  {
    return( stack.isEmpty() );
  }

  public Object peek()
  {
    if( empty())
      throw new EmptyStackException();
    return stack.lastElement();
  }
  public void push( Object theElement )
  {
    stack.addElement( theElement );
 }
  public Object pop()
  {
     if( empty())
        throw new EmptyStackException();
     Object top = ( stack.lastElement() ) ;
     stack.removeElementAt(stack.size() - 1);
     return top;
  }
}
  

