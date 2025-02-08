package com.quranvision.yolov9tflite.learnTools

import com.quranvision.yolov9tflite.R


object Constants {

    val titleIzhar:String = "Izhar Shafawee"
    val titleIdgham:String = "Idgham Shafawee"

    val expIzhar:String = "Izhar Shafawi means to pronounce the letters that follow meem sakin with no Ghunna and this rule applies to all Arabic letters except Ba and meem.\n\n" +
            "It is called Shafawi because the meem is produced by applying lips tightly above each other.\n\n" +
            "Remember the letter Fa and waw are close to the meem in their articulation point, so be careful to recite both two letters separately and clearly when they follow meem sakinah.\n\nFor example: “أزكى لكم وأطهر”، “ولهم فيها"

    val expIdgham:String = "The rule applies when a meem sakinah is followed by another meem. In this case, The reciter will merge the two meems and become one meem mushaddada with Ghunna that lasts for two counts.\n" +
            "\n" +
            "It is Also known as Idgham Mithlain Sagheer, because the two letters share the same articulation point (Maharaj) and attributes, and the first letter is not voweled, while the second is voweled.\n" +
            "\n" +
            "In the Uthmani mushaf, Al Idgham Ash Shafawi is indicated by the first meem being without a sign, and the second meem having a shadda on it."

    fun getAudioIzhar():ArrayList<AudioTajweed>{
        val audioList = ArrayList<AudioTajweed>()

        val audioListOne= AudioTajweed(
        1,
        "Izhar",
        R.drawable.anamta,
        "Meem + Ta",
        R.raw.alfatihah_izhar
    )
        audioList.add(audioListOne)

        val audioListTwo= AudioTajweed(
            2,
            "Izhar",
            R.drawable.rum17,
            "Meem + Sin",
            R.raw.rum17
        )
        audioList.add(audioListTwo)

        val audioListThree= AudioTajweed(
            3,
            "Izhar",
            R.drawable.abbas32,
            "Meem + Wau",
            R.raw.abbas32
        )
        audioList.add(audioListThree)

//        val audioListFour= AudioTajweed(
//            4,
//            "Izhar",
//            R.drawable.quiz4,
//            "Meem bertemu Wau",
//            R.raw.alfatihah_izhar
//        )
//        audioList.add(audioListFour)
//
//        val audioListFive= AudioTajweed(
//            5,
//            "Idgham",
//            R.drawable.quiz5,
//            "Meem bertemu Meem",
//            R.raw.alfatihah_izhar
//        )
//        audioList.add(audioListFive)
    return audioList
    }

    //TODO:get audio Idgham la lololll
    fun getAudioIdgham():ArrayList<AudioTajweed>{
        val audioList = ArrayList<AudioTajweed>()

        val audioListOne= AudioTajweed(
            1,
            "Idgham",
            R.drawable.humazah8,
            "Meem + Meem",
            R.raw.humazah8
        )
        audioList.add(audioListOne)

        val audioListTwo= AudioTajweed(
            2,
            "Idgham",
            R.drawable.buruj20,
            "Meem + Meem",
            R.raw.buruj20
        )
        audioList.add(audioListTwo)

        val audioListThree= AudioTajweed(
            3,
            "Idgham",
            R.drawable.mursalat20,
            "Meem + Meem",
            R.raw.mursalat20
        )
        audioList.add(audioListThree)

//        val audioListFour= AudioTajweed(
//            4,
//            "Izhar",
//            R.drawable.quiz4,
//            "Meem bertemu Wau",
//            R.raw.alfatihah_izhar
//        )
//        audioList.add(audioListFour)
//
//        val audioListFive= AudioTajweed(
//            5,
//            "Idgham",
//            R.drawable.quiz5,
//            "Meem bertemu Meem",
//            R.raw.alfatihah_izhar
//        )
//        audioList.add(audioListFive)
        return audioList
    }
}