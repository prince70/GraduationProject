package com.niwj.graduationproject.exam;


/*
 * 
 */
public class RQueue {
	  //寰幆闃熷垪
		private int _In;
	    private int _Out;
	    private int[] _ReciBuffer;

	     public RQueue(int buffersize)
	     {
	         _ReciBuffer = new int[buffersize];
	         _In = _Out = 0;
	     }

	     public void Empty()
	     {
	         _In = _Out = 0;
	        
	     }

	     public int Count()
	     {

			if (_In >= _Out) {
				return _In - _Out;
			} else {
				
				int i = _ReciBuffer.length;
				
				i = i;
				return _In + _ReciBuffer.length - _Out;

			}
			
			

	     }

	     public boolean IsEmpty()
	     {
			if (_In == _Out) {
				return true;
			} else {
				return false;
			}
	         
	     }

		// _In鍒板熬閮ㄧ殑涓暟
		public int TailEmptyCount() {

			if (_In == _ReciBuffer.length - 1) {
				// In鍦ㄥ熬閮�
				if (_Out == 0) {
					// 涓擮UT鍦ㄥご閮紝婊�
					return 0;
				} else {
					return 1;
				}
			} else {
				if (_In >= _Out) {
					return _ReciBuffer.length - 1 - _In;
				} else {
					return _Out - _In - 1;
				}
			}

		}

	     public int PopByte()
	     {
	         if (_In == _Out)
	         {
	             return 0;
	         }

	         int temp;
	         temp = _ReciBuffer[_Out];
	         if (++_Out >= _ReciBuffer.length)
	         {
	             _Out = 0;
	         }
	         return temp;
	     }

	     public void Push(int sdat)
	     {
	         _ReciBuffer[_In] = sdat;
	         if (++_In >= _ReciBuffer.length)
	         {
	             _In = 0;
	         }
	         
	        
	     }

	     public int ReadByte(int Location)
	     {
	         int i;
	         i = _Out + Location;
	         if (i >= _ReciBuffer.length)
	         {
	             i -= _ReciBuffer.length;
	         }
	         return _ReciBuffer[i];
	     }

		public int[] Buffer() {
			return _ReciBuffer;
		}

		public int InLocation() {
			return _In;
		}

		public int OutLocation() {
			return _Out;
		}

	     public void MoveIn(int count)
	     {
	         _In += count;
	         if (_In >= _ReciBuffer.length)
	         {
	             _In = 0;
	         }
	     }

}
