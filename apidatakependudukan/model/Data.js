const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    kodeData:{
        type: String
    },
    namaWarga : {
        type: String
    },
    nik : {
        type: String
    },
    jenisKelamin : {
        type: String
    },
    alamat : {
        type: String
    },
    agama : {
        type: String
    },
    gambar : {
        type: String
    }
})

module.exports = mongoose.model('data',userSchema)
