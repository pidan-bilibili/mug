# numc
In numc, we implement the interface of python. We are connecting our code in C and in python. Unlike in C, where we spent a lot of time trying to check things like mat1-> rows == result->rows, we did a lot of more upper level check. For isntance, we need to check if the correct type are in the args. We also need to check if the space are allocated correctly. We learned a lot from this part. For example, we have learned how to use unpack_tuple to parse the args and check each condition. This task is not so hard but very important for this project. To add one that, where we learned the most is in Matrix.c, we finally get to see the underlayer of librarys like numpy. How they are working, and why they are much faster than others.

### Provide answers to the following questions for each set of partners.
- How many hours did you spend on the following tasks?
  - Task 1 (Matrix functions in C): ???
  - Task 2 (Writing the Python-C interface): ???
  - Task 3 (Speeding up matrix operations): ???
- Was this project interesting? What was the most interesting aspect about it?
  - <b>Jerry : I personally think this project is very interesting and inspiring.I've used numpy alot in cources like Data100 and STAT140. I always wonder why this library can do arithmatic and other operation much faster than others? And it is here where I got the anwser. SIMD and Parallel just opened my eyes and introduced me to the meaning of computer science once again. I can get my matrix so much faster by exploiting those feature, and I do not even need to know how to implementing them. After learning all those data structures from 61B, I saw another way to speed up my functions and I'm very excited to use these in the future. In fact, this is my favourite part of this class. </b>
  - <b>MuYi : Yes. For a math major, I like to see the connection between computer science and math. I like the pow function a lot. In math, to speed up doing power, we can use diagnolization and matrix decomposition, however, in computer science, we can use algorithm like repeated squares. I like to learn different way to solve a problem. Also, SIMD and parallel are some super new concept to me and I'm glad I get to implement them myself in this project. This makes me understand even more.</b>
- What did you learn?
  - <b>Jerry : I learned a lot! I learned how to use SIMD and OpenMP, I also learned some cool algorithms like repeated squares. Not only do I get a chance to optimize some arithmetic operation, but I also get a chance to understand the CPU more!</b>
  - <b>MuYi : I learned more on how to apply parallel (OpenMP) and SIMD to my code. I also see the connection between python and C. By the way, In this project, I also learned a lot from writing tests, debugging and communicating with my partner.</b>
- Is there anything you would change?
  - <b>Jerry :In terms of timing, I would start earlier with my partner, I was having trouble catching up with the class, so I didn't get to start early. I would take more time to catch up sooner communicate more with my partner at the beginning. 
       MuYi: I would got myself more familiar with SIMD and Parallel programming before starting the project. I started before I felt confident on these subject, so I felt a little rusty when we are doing task 3.</b>

### If you worked with a partner:
- In one short paragraph, describe your contribution(s) to the project.
  - <b>Jerry: I took care of checking and helping debugging for task 1, and I wrote add, abs, and mul in task 3. I went to OH and communicate with MuYi what we didn't know and missed before.</b>
- In one short paragraph, describe your partner's contribution(s) to the project.
  - <b>Jerry : MuYi wrote all the task 1. And we wrote task 2 together. He took a lot of time debugging task 1 and 2. He also helps me opitimizes taks 3 add, abs and mul. We wrote pow together. </b>
