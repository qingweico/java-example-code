#include <stdio.h>
int main(){
	int a = 1;
	int b = 2;
	 

	 // const pointer
	 // The point of a pointer can be changed, but the value of a pointer cannot be changed.
	const int* p = &a;
	p = &b;
	//error  
	// *p = 100;
	
	// pointer const
	// The point of a pointer cannot be changed, but the value of a pointer can be changed.
	int* const t = &b;
	*t = 100;
	// error 
	// t = &b; 
}
