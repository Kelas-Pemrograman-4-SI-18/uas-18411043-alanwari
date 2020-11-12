const data = require('../model/data.js')
const  response = require('../config/response')
const mongoose = require('mongoose')
const ObjectId = mongoose.Types.ObjectId


exports.inputDatapenduduk = (data, gambar) =>
    new Promise(async (resolve, reject)=> {

        const databaru = new data({
            kodeData: data.kodeData,
            namaWarga: data.namaWarga,
            nik: data.nik,
            jenisKelamin: data.jenisKelamin,
            alamat: data.alamat,
            agama: data.agama,
            gambar: gambar
        })

        await  data.findOne({kodeData: data.kodeData})
            .then(data =>{
                if(data){
                    reject(response.commonErrorMsg(' kode sudah digunakan'))
                }else {
                    databaru.save()
                        .then(r =>{
                            resolve(response.commonErrorMsg('berhasil input data'))
                        }).catch(err =>{
                        reject(response.commonErrorMsg(' gagal'))
                    })
                }
            }).catch(err => {
                reject(response.commonErrorMsg(' terjadi kesalahan, input gagal'))


            })
    })

exports.lihatData = () =>
    new Promise(async (resolve, reject) =>{
        await data.find({})
            .then(result =>{
                resolve(response.commonResult(result))
            })
            .catch(() => reject(response.commonErrorMsg(' terjadi kesalahan')))
    })

exports.lihatDetailData = (kodeData) =>
    new Promise(async (resolve, reject) =>{
        await data.findOne({kodeData: kodeData})
            .then(result =>{
                resolve(response.commonResult(result))
            })
            .catch(() => reject(response.commonErrorMsg(' terjadi kesalahan')))
    })



exports.updateData = (id, data, gambar) =>
    new Promise(async (resolve, reject)=>{
        await data.updateOne(
            {_id : ObjectId(id)},
            {
                $set: {
                    kodeData: data.kodeData,
                    namaWarga: data.namaWarga,
                    nik: data.nik,
                    jenisKelamin: data.jenisKelamin,
                    alamat: data.alamat,
                    agama: data.agama,
                    gambar: gambar
                }
            }
        ).then( data => {
            resolve(response.commonSuccessMsg('berhasil mengubah data'))
        }).catch(err => {
            reject(response.commonErrorMsg(' terjadi kesalahan'))
        })
    })

exports.hapusData = (_id) =>
    new Promise(async (resolve, reject) => {
        await data.remove({_id: ObjectId(_id)})
            .then(() => {
                resolve(response.commonSuccessMsg('berhasil menghapus data'))
            }).catch(() => {
                reject(response.commonErrorMsg(' terjadi kesalahan'))
            })
    })
