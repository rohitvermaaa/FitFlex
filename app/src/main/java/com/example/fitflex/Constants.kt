package com.example.fitflex

object Constants {
    fun defaultExerciseList() : ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()

        val crunches = ExerciseModel(
            id = 1,
            name = "Crunches",
            image = R.drawable.ic_crunches,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(crunches)

        val bentLegRaises = ExerciseModel(
            id = 2,
            name = "Bent Leg Raises",
            image = R.drawable.ic_bent_leg_raise,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(bentLegRaises)

        val bicycleCrunch = ExerciseModel(
            id = 3,
            name = "Bicycle Crunch",
            image = R.drawable.ic_bicycle_crunch,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(bicycleCrunch)

        val dumbbellFrontRaises = ExerciseModel(
            id = 4,
            name = "Dumbbell Front Raises",
            image = R.drawable.ic_dumbell_front_raise,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(dumbbellFrontRaises)

        val dumbbellLateralRaises = ExerciseModel(
            id = 5,
            name = "Dumbbell Lateral Raises",
            image = R.drawable.ic_dumbell_lateral_raise,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(dumbbellLateralRaises)

        val highKneeRun = ExerciseModel(
            id = 6,
            name = "High Knee Run",
            image = R.drawable.ic_high_knee_run,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(highKneeRun)

        val legRaises = ExerciseModel(
            id = 7,
            name = "Leg Raises",
            image = R.drawable.ic_leg_raise,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(legRaises)

        val lunges = ExerciseModel(
            id = 8,
            name = "Lunges",
            image = R.drawable.ic_lunges,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(lunges)

        val plank = ExerciseModel(
            id = 9,
            name = "Plank",
            image = R.drawable.ic_plank,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(plank)

        val pushup = ExerciseModel(
            id = 10,
            name = "Pushup",
            image = R.drawable.ic_pushup,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(pushup)

        val squats = ExerciseModel(
            id = 11,
            name = "Squats",
            image = R.drawable.ic_squats,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(squats)
        return exerciseList
    }
}