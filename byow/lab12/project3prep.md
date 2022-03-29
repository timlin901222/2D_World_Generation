# Project 3 Prep

**For tessellating hexagons, one of the hardest parts is figuring out where to place each hexagon/how to easily place hexagons on screen in an algorithmic way.
After looking at your own implementation, consider the implementation provided near the end of the lab.
How did your implementation differ from the given one? What lessons can be learned from it?**

Answer: Staff solution not yet posted. 

-----

**Can you think of an analogy between the process of tessellating hexagons and randomly generating a world using rooms and hallways?
What is the hexagon and what is the tesselation on the Project 3 side?**

Answer: The process of tessellating hexagons are similar to generating hallways that are directly connected to rooms. 
We also need to make sure rooms don't collide with one another. Tesselation is the process of drawing rooms and pathways,
and hexagons are the rooms.

-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating hexagons.**

Answer: I would start by writing room-generating and path-generating methods. Then, I will tackle the conctenation of 
randomly generated rooms.

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: A hallway is a n by 3 space where it is length n in the vertical or horizontal direction 
and 3 in the horizontal or vertical direction. It consists of room-space sandwiched between walls. Rooms are 
n by m space where n and m are any integer. The outer edge is surrounded by walls or space into a hallway.
