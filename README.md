# (UNNAMED PROTO LANGUAGE)

This is a prototype of a programming language that implements a few features
I've been thinking about for some time.
It is designed mostly for thinking about.

## Proposed feature listing

### Core features

These features are core to the design, and are very unlikely to change very
much.

- No keywords
- A sleek, secure way of having per-file syntax overloading.
  - Syntax modules included at the beginning of the file, much like imports
- Don't have some stupid feature such as lazy eval forced; allow for devs to
  wrap everything in a function if they wanted to.
- Any operator may be applied to any variable, at all.
  - EX: while `7*10` returns `70`, a function that returns 7 multipled by a
  function that returns 10 returns a function that returns the result of that
  operator applied to each value, respectively.

#### CORE PROPOSAL 1

Add "everything is a function - excluding natives"

#### CORE PROPOSAL 2

Add "everything is a function - including natives"

### Other features

- Object inheritance
  - Planned to be much like TypeScript, but with functions at the top of the
  tree.
  - To be implemented by importing a library. This ipmlementation may even have
  a syntax module availiable.
- Keywords, inline xml, obj syntax sugar all to be syntax modules.

### Syntax

#### Comments

```text
//Single-line C++ style.

/*
Along with muli line C style are both supported
*/

//* single line comments are evaluated first

And_thus="this line is still reached and evaluated";

/**
 * This type of comment is intended documentation for smooth integration with
 * your IDE, if it supports it. Must be valid markdown, but type indicators
 * may be accepted.
 *
 * No IDE's are known to support the language more then they would a .txt file.
 * Replace this with a labled list of known IDE's in the future. If the count
 * is greater then 20, remove this list.
 */
```

#### Variables

Variables may be set to the following (`Generator` is the constructor):

- `Function`
- `Number`
  - Supports all syntaxes java supports for numbers.
    - decimal `34`
    - bin `0b11`
    - hex `0xf4`
    - octal `012`
  - Assumes the smallest size for every number type possible. When size needs to
  be bigger, it goes the next size up. (in bits; 8, 16, 32, 64). It also will be
  unsigned by default, and change signage.
    - These changes may cause lag
    - are irreversable
    - Will effect other vars, as when operations are done on two vars, they are
    both casted to the larger bitsize of the two.
    - Signage changes will be computed seperatly in all operations excluding
    addition and subtraction
- `Char` (C-style)
- `Array` (C-style)
- `String` (C-style)
- `Bool` (C-style)

Naming conventions are identical to JavaScript; they may contain any number,
upper or lowercase letter, or these: `$_`. They cannot start with a number.

```text
newVar=3; // number
anotherVar=()=>3; //function returning a number
_yetanoth3rVar='c'; // this is just a char, but can be cast to and from a number
$another="Note that this is basically just a C-string";
theLast1=true;//And here's a bool.
```

##### Variables proposal 1

Make `String` a syntax module?

#### Typecasting

Variables are never cast automatically, with the exeption of numbers
automaticaly increasing their bitsize (see above).

To cast use the constructor like so:
`joinOfStringAndNum="The number is "+String(37)`

- To `Boolean`
  - `Boolean` duplicate of original boolean
  - `Char` error thrown: `Casting error: Cannot cast Char to Boolean`
  - `Number` true only if equal to `0` or `-0`
  - `Array` error thrown: `Casting error: Cannot cast Array to Boolean`
  - `String` error thrown: `Casting error: Cannot cast String to Boolean`
  - `Function` error thrown: `Casting error: Cannot cast Function to Boolean`
- To `Char`
  - `Boolean` `'t'` if true, `'f'` if false
  - `Char` duplicate of original char
  - `Number` local charcode conversion to char. If invalid, throw sensible error
  like `Casting error: Cannot cast Number(`insert num here`) to Char` as it is
  value dependant.
  - `Array` error thrown: `Casting error: Cannot cast Array to Char`
  - `String` error thrown: `Casting error: Cannot cast String to Char`
  - `Function` error thrown: `Casting error: Cannot cast Function to Char`
- To `Number`
  - empty, throw `Casting error: Number constructor takes 1 argument`
  - `Boolean` `0` if true, `1` if false
  - `Char` local charcode conversion to num. If invalid, throw sensible error
  like `Casting error: Cannot cast Char(`insert char here`) to Number` as it is
  value dependant.
  - `Number` duplicate of original num
  - `Array` error thrown: `Casting error: Cannot cast Array to Number`
  - `String` If the string is what would pass for a valid in-line literal of any
  type (decimal, hex, etc.) then resolve it, elsewhise, throw:
  `Casting error: Cannot cast String(`insert string here`) to Number`
  - `Function` error thrown: `Casting error: Cannot cast Function to Number`
- To `Array`
  - empty, array len of 0
  - `Boolean` error thrown: `Casting error: Cannot cast Boolean to Array`
  - `Char` error thrown: `Casting error: Cannot cast Char to Array`
  - `Number` empty array of the length of that number. If not enough memory,
  error thrown: `Casting error: Cannot cast Number(`insert num here`) to Array`
  - `Array` duplicate of origianl array
  - `String` Array of chars of original string.
  - `Function` error thrown: `Casting error: Cannot cast Function to Number
- To `String`
  - Empty, empty string.
  - `Boolean` `"True"` if true, `"False"` if false.
  - `Char` The char is turned into a string with len of 1
  - `Number` Best representation of data as a string via `Number.toString()`
  - `Array` if the array is only chars, convert it, elsewise,
  error thrown: `Casting error: Cannot cast non Char Array to String.`
  - `String` duplicate of original string
  - `Function` error thrown: `Casting error: Cannot cast Function to String`
- To `Function` all cases: return a function that returns the input, unless it
is empty, then throw `Casting error: Function constructor takes 1 argument`

#### Operators

All primitive types allow one of these operators to be appllied to another of
the same type, with the exeption of functions, where they don't accept `++` or
`--` operators.

> Many of these may be dropped in order to replace them with a functional
> equivilant.

- Add `+`
- Subtract `-`
- Multiply `*`
- Divide `/`
- Increment `++` (prefix v postfix act the same as in C)
- Decrement `--` (prefix v postfix act the same as in C)
- Increment by `+=`
- Decrement by `-=`
- Multipy by `*=`
- Divide by `/=`
- Left byteshift `<<`
- Right byteshift `>>`
- Greater then `>`
- Less then `<`
- Greater then or equal to `>=`
- Less then or equal to `<=`
- AND `&&`
- OR `||`
- XOR `^`
- Equal `==`
- SET `=`
- Invert bool `!`

#### Functions

##### Defining

Supports 6 ways of declaring functions, listed below

###### No args

```text
anyVariableHere={
  //do something upon invoction
}
```

###### No args One line

```text
anyVariableHere=()=> 72;// return 72
```

###### One Arg

```text
anyVariableHere= in => {
  self.ret(in*2); // return the arg times two
};
```

###### One arg One Line

```text
anyVariableHere= in => in*2;//a single line is treated like a code block
```

###### Multi-arg

```test
anyVariableHere= (a,FEW,dif_ferent,var5,h$r3) => {
  //The => is optional when inbetween an endparen and an opening curly
  self.ret(a+FEW+dif_frent+var5+h$er3);
}
```

###### Multi-arg One line

```test
anyVariableHere= (a,FEW,dif_ferent, var5,h$r3) => a+FEW+dif_frent+var5+h$er3;
```

##### scoping

```text
//can be modified above theFunc
//can be modified within theFunc
//can be modified below theFunc
a=1947;
theFunc=(
  //can't be modified above theFunc
  //can be modified within theFunc
  //can be modified below theFunc (after a `anotherVar` is declared)
  e)=>{
  //can't be modified above theFunc
  //can be modified within theFunc
  //can't be modified below theFunc
  b=28142;
  //can't be modified above theFunc
  //can be modified within theFunc
  //can be modified below theFunc
  self.c=4234;

  e++;
}
//can't be modified above theFunc
//can't be modified within theFunc
//can be modified below theFunc
d=324;

/* here's how to modify this var too.
 * note that it isn't initialized till there is a value set to it.
 * If you read this variable on the first line of `theFunc` it would read `7`*/
theFunc.c=7;

//can't be modified above theFunc
//can be modified within theFunc
//can be modified below theFunc (but only after this call)
anotherVar=99;

theFunc(anotherVar);
//anotherVar now equals 100
```

Aditionally, functions can be defined within other functions. As they are stored
within variables, they follow the same scoping when it comes to running the
function. This also applies to variables within functions within functions:

```text
//cannot read or execute outerFunc here
//cannot read or execute innerFunc here
//cannot read a here
outerFunc={
  //can read &/or execute outerFunc here
  //cannot read or execute innerFunc here
  //cannot read a here
  innerFunc={
    //can read &/or execute innerFunc here
    //can read &/or execute outerFunc here
    //cannot read a here
    a=true;
    //can read a here
  }
  //can read &/or execute innerFunc here
  //cannot read a here
}
//can read &/or execute outerFunc here
//cannot read or execute innerFunc here
//cannot read a here
```

###### When var names collide

Sometimes, variable names are unintentially reused by devs. Here's an example of
functional, yet poorly written code.

```text
conflicingName=849234;
funcName=conflictingName=> conflicingName();

funcName(() => 21); //returns 21

anotherName=(conflicingName) => {
  self.ret(conflictingName++);
}

k=3;
anotherName(k)//returns 3
//k is now 4

//conflictingName is still 849234
```

##### Returning

###### From iside a one-liner

```text
returnsNumber1=()=>1;
returnsArgTimesTwo=arg=>arg*2;
returnsTheSumOfArgs=(a,b)=>a+b;
```

###### From a block function

```text
returnsNumber1={
  self.ret(1);
};
returnsArgTimesTwo=arg=>{
  self.ret(arg*2);
}
returnsTheSumOfArgs=(a,b) => {
  self.ret(a+b);
}
```

##### Calling convention

```text
a=()=>1;
b=a=>a+1;
c=(a,b)=>a+b;

//Call a
a();
//call b, passing a value. (if a var, it's a pointer)
b(3);// returns 4
c(3,7); //returns 10
```

### Reserved functions

#### if

- Takes 2 args, a boolean and a function called when boolean is true.
- An optional third arg is another function, called if boolean is false.

If returns a function that resolves itself. Untill this is run, the if is not
evaluated.

```text
if(true,{
  //code if true
})()
```

#### import

- Takes 2 args
  - A string that must refer to either a module name, a Unix-style url to a
file (extention not needed), or to a URL resorce that the OS can handle.
- Returns a promise (think JavaScript promise) - TODO
- When module is called, the module has no access to anything else and is
gennerally treated as its own seperate, private program. Said module may
also call import. Furthermore, the module exports by returning at the top level.

#### useSyntax

What I'm going for here is a clean way where the file "imports" a library, and
that library then allows for alien syntax below that import

##### useSyntax proposal 2

- Takes 2 args
  - A string with the same requirements as arg 0 of *import*
  - An optional variable that is one of the following:
    - An array of strings with the below description
    - A string that exactly matches the name of the syntax conversion defined by
    `declareSyntax`.
- Use RegExp?

##### useSyntax proposal 1

- Takes 1 arg, a string with the same requirements as arg 0 of `import`
- Calls the function passed into `declareSyntax`, passing in the entirety of
the source code. The return of the function (a string) will then be treated as
the new code, and the next call to this particular *useSyntax* within this file
will be ignored, thus preventing recursion. (Note that this means that all code
up till this point is called again. This is why it is bad practice, but it is
not unnallowed, for the function to be called outside of the logical "header".
This may, unfortunately cause minor issues for compilers, and they are not
obligated to support more then top-level calls to this function)
- result must be code that either other *useSyntax* calls (including ones
injected with a *useSyntax* call) in lower lines of code will be able to
transpile, not to mention the compiler/interpreter iself must be able to
understand.

#### declareSyntax

##### declareSyntax Proposal 1

Takes 1 arg, a function that takes the entire file following the *useSyntax*
call as its only argument, returning the modified code to be ran.

> Can be bad, due to possibility of injection, and the fact that the lib needs the
> whole file.

##### declareSyntax Proposal 2

take an object, where the keys are the names of the conversion, and the values
are arrays where item zero is a regexp that the suntax must match, and item one
is a function that the matched code is passed into. Return of that function
replaces the regexp match.

take an object as follows (in psudo json):

```text
"NameOfSyntaxConversion":[/regexpThin/g,(entiretyOfTheLine_s_thatMatched) {
  //return a string to replace it
}]
```

> Still has possibility for injection, but simplifies a few steps.

#### Regexp

?? only if needed by declareSyntax proposal 2

A new regexp element.
