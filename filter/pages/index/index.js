//index.js
//获取应用实例
var app = getApp()

function getValueTxt(values){
    return values.map(item => item.name +": "+ item.value).join(";")
}

function getValueParams(values){
    return values.map(item=> item.pname +"=" +item.value).join("&")
}

var defvalues = [
    {name:"灰度", pname:"grayscale",value: 0},
    {name:'复古', pname:"sepia", value:0},
    {name:'饱和度', pname:"saturate", value:1},
    {name:'色相旋转', pname:"hue-rotate", value:0},
    {name:'反色', pname:"invert", value:0},
    {name:'透明度', pname:"opacity", value:1},
    {name:'亮度', pname:"brightness", value:1},
    {name:'对比度', pname:"contrast", value:1},
    {name:'模糊', pname:"blur", value:0}]
var values = [].concat(defvalues)

Page({
  data: {
    values:values,
    valueTxt: getValueTxt(values),
    remoteimg: '/images/image.jpg',
    list: [{
        zh:"灰度",
        en:"grayscale",
        defvalue: 0,
        min: 0,
        max: 1
    },{
              zh:"复古",
              en:"sepia",
              defvalue: 0,
              min: 0,
              max: 1
    },{
        zh: "饱和度",
        en:"saturate",
        defvalue: 0.2,
        min: 0,
        max: 5
    },{
             zh: "色相旋转",
             en:"hue-rotate",
             defvalue: 0,
             min: 0,
             max: 360
    },{
        zh: "反色",
        en:"invert",
        defvalue: 0,
        min: 0,
        max: 1
    },{
         zh: "透明度",
         en:"opacity",
         defvalue: 1,
         min: 0,
         max: 1
     },{
        zh: "亮度",
        en:"brightness",
        defvalue: 0.2,
        min: 0,
        max: 5
    },{
      zh: "对比度",
      en:"contrast",
      defvalue: 0.2,
      min: 0,
      max: 5
  },{
         zh: "模糊",
         en:"blur",
         defvalue: 0,
         min: 0,
         max: 5
     }]
  },
  onLoad: function () {

  },
  reset: function(e){
    values = [].concat(defvalues)
    this.setData({
        values: values,
        valueTxt: getValueTxt(values),
        list: this.data.list
    })
  },
  submit: function(e){
    let url = "/images/image.jpg"
    url = app.globalData.server+"submit?"+getValueParams(this.data.values)
    this.setData({
        remoteimg: url
    })
    console.log(url)
    wx.showToast({
        title: "加载图片",
        icon: "loading",
        duration: 2000
    })
  },
  loadsuccess: function(e){
    console.log(e)
    wx.hideToast()
  },
  loaderror: function(e){
    console.log(e)
    wx.hideToast()
  },
  slider4change: function(e){
    let value = e.detail.value
    let index = e.currentTarget.dataset.index
    let objv = null
    let objb = null
    console.log()
    switch(index){
    case 10:
        break;
    default:
        objv = this.data.values[index]
        objb = this.data.list[index]
        objv.value = (value * (objb.max- objb.min)).toFixed(2)
        this.setData({
            values : this.data.values,
            valueTxt: getValueTxt(this.data.values)
        })
    }
  }
})
