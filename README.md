# SOFT252 Assignment

While this project is relatively young, it was not the first iteration of it. To show case my journey, I have links to the other attempts I had at this assignment. I learnt a lot from them and this final version is product of those previous attempts. The README of each provide details as to why I started again:
* [SOFT252-Assignment-IDEA](https://github.com/BarkerDevelopment/SOFT252-Assignment-IDEA)
* [SOFT252-Assignemtn-NetBeans](https://github.com/BarkerDevelopment/SOFT252-Assignment-NetBeans)

## Example Login Details

Here are a couple of example logins to get past the login page.

|Login|Password  |Use Case |
|-----|:--------:|:--------|
|P1902|terminator|Patient  |
|D4891|let_me_in |Doctor   |
|S2844|frag&crack|Secretary|
|A4212|admin     |Admin    |

## Desing Patterns Used

* **MVC** - For project structure.
* **Observer** - For a relationship between PrescriptionRequest and DrugStock. PrescriptionRequest keeps track of the drug it will prescribe's stock so that if it is approved, it knows if there is sufficient stock in the system or not.
* **Factory** - For Feedback and ID creation. Seperates the creation logic from the model.
* **State** - For the login controllerr, to keep track as to whether a user is logged in or not; if so, it stores the user.
* **Singleton** - For the majority of controlls as one and only instance of each is required throughout the system.
* **Strategy** - For the RepositorySerialiastionController class. This allows other serialisation strategies to be used easily.
* **Template** - For RequestRepositoryController and Request classes. The RequestRepositoryController contains the template that uses the Request class's approveAction and denyAction functions.
* **Command** - For ViewController class. This class handles moving the user through different forms, it stores all the I_Forms it is passed and allows one to return back to a previous form (which is used extensively).

## Credit
* https://www.fantasynamegenerators.com/warhammer-40k-space-marine-names.php
* https://www.fantasynamegenerators.com/warhammer-40k-sisters-of-battle-names.php
* https://en.wikipedia.org/wiki/Postcodes_in_the_United_Kingdom
