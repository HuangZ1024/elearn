<template>
  <div class="csv-check">
    <h1>CSV一括登録</h1>
    <div class="steps">
      <!-- <el-steps :active="active" process-status="finish"  finish-status="finish" align-center> -->
      <el-steps :active="active" align-center>
        <el-step title="步骤 1"></el-step>
        <el-step title="步骤 2"></el-step>
        <el-step title="步骤 3"></el-step>
        <el-step title="步骤 4"></el-step>
        <el-step title="步骤 5"></el-step>
      </el-steps>
    </div>

    <div class="upload">
      <h4>商品資料アップロード</h4>
      <div class="file-select">
        <el-upload action="none" :on-change="handleFileUpload" :auto-upload="false" :show-file-list="false">
          <el-button size="small" type="success">ファイル選択</el-button>
        </el-upload>
        <!-- <span v-if="file">{{ file.name }}</span> -->
        <div class="file-name">
          <span>{{ file == null ? ".CSV" : file.name }}</span>
          <hr :class="active > 0 ? 'green-line' : ''" />
        </div>
      </div>
    </div>

    <div class="upload-image" v-if="active > 1">
      <h4>関連画像</h4>
      <div class="imgDisp" v-if="active > 2 && imageList.length != 0">
        <ul>
          <li v-for="(img, index) in imageList">
            <div id="delete" v-if="active == 3">
              <i @click="removeImg(index)" class="deleteImg el-icon-close"></i>
            </div>
            <div>
              <img :src="img.objectUrl" width="100px" />
              <!-- <p>{{ index }}</p> -->
            </div>
          </li>
        </ul>
      </div>

      <el-upload v-if="active == 2 || active == 3" action="none" :on-change="handleImageUpload" :auto-upload="false" :show-file-list="false">
        <el-button id="add-image-btn" type="primary" round>追加</el-button>
      </el-upload>
    </div>

    <div class="button-area">
      <div>
        <el-button id="check-btn" v-if="active == 1" @click="checkCSV" round>CSVチェック</el-button>
      </div>
      <div>
        <el-button id="check-btn" v-if="active == 3" @click="checkImageList" round>画像チェック</el-button>
      </div>

      <div class="submit-reset">
        <el-button id="submit-btn" v-if="active == 4" @click="submit" round>一括登録</el-button>
        <el-button id="reset-btn" v-if="active > 0" @click="reset" round>最初からやり直し</el-button>
      </div>
    </div>

    <div id="testData">
      <ul v-if="testData.imgList != null">
        <li v-for="img in testData.imgList">
          <img :src="img.image" width="100px" />
        </li>
      </ul>
    </div>
  </div>
  <!-- 商品資料アップロード ファイル選択　 -->
</template>
  
  <script>
// _和驼峰的转换可能出问题
import axios from "axios"
export default {
  name: "CsvCheck",
  components: {},
  watch: {},
  data() {
    return {
      active: 0,
      // fileList: [],
      file: null, //.raw
      imageList: [],
      img: {
        objectUrl: null, //给img src用的
        dataUrl: null, //基于base64编码的Data URL
        fileName: null,
        type: null,
      },
      itemList: [], //转化为obj后的item的list
      // item: {
      //   name: "n2",
      //   bookmark_count: "b2",
      //   order_count: "o2",
      //   anonymous: "実名",
      //   access: "一般公開",
      //   image_file: "",
      // data
      // },
      testData: {
        imgList: null,
      },
      Vo: {
        item: null,
        img: null,
      },
    }
  },
  created() {
    // this.test()
  },
  methods: {
    reset() {
      this.file = null
      this.imageList = []
      this.itemList = []
      this.active = 0
    },
    handleFileUpload(file) {
      console.log("handleFileUpload")
      console.log(file)
      // console.log(file.raw)

      if (file.raw.type != "text/csv") {
        this.$message.error("CSVファイル形式ではありません。正しいファイルを再度アップロードしてください。")
        console.log(file.raw.type)
        return
      }

      this.checkColumn(file.raw)
    },
    checkColumn(file) {
      //检查列名是否一致，异步！！！

      console.log("checkColumn")

      //读取文件为Text
      const reader = new FileReader()
      reader.readAsText(file)
      // let array = reader.readAsArrayBuffer(file)

      reader.onload = (e) => {
        console.log("reader.onload")
        // console.log(e.target.result)
        let text = e.target.result

        const lines = text.split("\r\n")
        const columnNames = new Set(lines[0].split(","))

        const shiyou = new Set(["name", "bookmark_count", "order_count", "anonymous", "access", "image_file"])

        // console.log(columnNames)
        // console.log(shiyou)

        if (columnNames.size != shiyou.size) {
          this.$message.error("メッセージ内容：ファイルの内容が仕様を満たしていません。正しいファイルを再度アップロードしてください。")
        }

        for (let column of columnNames) {
          if (!shiyou.has(column)) {
            this.$message.error("メッセージ内容：ファイルの内容が仕様を満たしていません。正しいファイルを再度アップロードしてください。")
          }
        }

        this.file = file
        this.active = 1
      }
    },
    checkCSV() {
      //检查文件内容是否合规
      // console.log(file)

      if (this.file == null) {
        this.$message.error("ファイルがアップロードされていません。ファイルをアップロードしてください。")
        return
      }

      //读取文件为Text
      const reader = new FileReader()
      reader.readAsText(this.file)
      // let array = reader.readAsArrayBuffer(this.file)

      reader.onload = (e) => {
        console.log("reader.onload")
        // console.log(e.target.result)
        let text = e.target.result

        let result = this.csvTextToObjects(text)
        if (result == null) {
          //消息弹的太快会重叠起来，延迟一下
          setTimeout(() => {
            this.$message.error("ファイルの内容が正しくないためチェック失敗しました。正しいファイルを再度アップロードしてください。")
          }, 1000)
        } else {
          this.$message.success("ファイルの内容をチェック成功しました。")
          this.itemList = result
          this.active = 2
        }
        console.log("csvTextToObjects.result", result)
      }

      //一个csv文件有一行数据还是多行数据
      //关联图像是怎么关联

      //读取的文件乱码怎么办，报错？乱码检测器有没有？转码？.

      //末行可能多出一个换行，导致多分出一行空数据
    },

    checkObject(obj) {
      //检查对象是否合规
      // console.log("checkObject", obj)

      if (obj.name == undefined) {
        console.log("该列不存在")
        return false
      } else if (obj.name == "") {
        this.$message.error("name項目が未入力。")
        return false
      } else if (obj.name.length > 100) {
        this.$message.error("name項目の長さ過ぎる。")
        return false
      }

      if (obj.bookmark_count == undefined) {
        console.log("该列不存在")
        return false
      } else if (obj.bookmark_count == "") {
        this.$message.error("bookmark_count項目が未入力。")
        return false
      }

      if (obj.order_count == undefined) {
        console.log("该列不存在")
        return false
      } else if (obj.order_count == "") {
        this.$message.error("order_count項目が未入力。")
        return false
      }

      if (obj.anonymous == undefined) {
        console.log("该列不存在")
        return false
      } else if (obj.anonymous == "") {
        this.$message.error("anonymous項目が未入力。")
        return false
      } else if (obj.anonymous != "匿名" && obj.anonymous != "実名") {
        this.$message.error("anonymous項目の値が仕様を満たしていません。")
        return false
      }

      if (obj.access == undefined) {
        console.log("该列不存在")
        return false
      } else if (obj.access == "") {
        this.$message.error("access項目が未入力。")
        return false
      } else if (obj.access != "一般公開" && obj.access != "限定公開") {
        this.$message.error("access項目の値が仕様を満たしていません。")
        return false
      }

      if (obj.image_file == undefined) {
        console.log("该列不存在")
        return false
      } else if (obj.file_name == "") {
        this.$message.error("file_name項目が未入力。")
        return false
      }

      return true
    },

    underlineToCamel(underline) {
      let array = underline.split("_")
      for (let i = 0; i < underline.length; i++) {
        for (let i = 1; i < array.length; i++) {
          array[i] = array[i].charAt(0).toUpperCase() + array[i].substring(1)
        }
      }
      return array.join("")
    },

    csvTextToObjects(csvText) {
      //不合规返回null，合规返回生成对象数组
      //检查第一行，分割出，列数，新建数组
      const lines = csvText.split("\r\n")
      // console.log("lines", lines)

      const columnNames = lines[0].split(",")
      // console.log("columnName", columnNames)

      const objs = []

      //遍历每行数据
      for (let i = 1; i < lines.length; i++) {
        const columnValues = lines[i].split(",")
        var obj = new Object()

        //遍历行中的列，将每列数据写入对象的属性值
        for (let j = 0; j < columnNames.length; j++) {
          obj[columnNames[j]] = columnValues[j]
        }

        //生成对象后，检查对象是否合规
        if (!this.checkObject(obj)) {
          console.log("checkObject", false)
          return null
        }

        objs.push(obj)
      }

      // console.log("objs", objs)
      return objs
    },
    handleImageUpload(file) {
      //初步检查，并且封装img入imageList
      console.log("handleImageUpload")
      console.log(file)
      // console.log(file.raw)
      if (!file.raw.type.startsWith("image/")) {
        this.$message.error("ファイルの型式が正しくないか、サイズが大きすぎる（画像の最大サイズ：5000x5000）ためアップロードできません。")
        console.log(file.raw.type)
        return
      }

      //读取文件，检查图片大小
      const reader = new FileReader()
      reader.readAsDataURL(file.raw)
      reader.onload = () => {
        let dataUrl = reader.result
        const image = new Image()
        image.src = dataUrl
        console.log("dataUrl", dataUrl)
        console.log(" dataUrl.length", dataUrl.length)
        image.onload = () => {
          // console.log("img", img)
          // console.log("width", img.width)
          // console.log("length", img.height)

          if (image.width > 5000 || image.height > 5000) {
            this.$message.error("ファイルの型式が正しくないか、サイズが大きすぎる（画像の最大サイズ：5000x5000）ためアップロードできません。")
          } else {
            // 封装img
            var img = {
              dataUrl: dataUrl,
              objectUrl: URL.createObjectURL(file.raw),
              fileName: file.name,
              type: file.raw.type,
            }

            //img存入数组
            this.imageList.push(img)

            this.active = 3

            console.log("imageList", this.imageList)
          }
        }
      }
    },
    checkImageList() {
      // img怎么存
      // 将图片以二进制方式储存在数据库中？
      // 地址存？
      try {
        if (this.imageList.length == 0) {
          this.$message.error("data項目が未入力。")
        }

        for (let img of this.imageList) {
          if (img.dataUrl.length > 200) {
            this.$message.error("data項目の長さ過ぎる。")
            return
          }
        }

        //检查所有图片的类型
        for (let img of this.imageList) {
          if (!img.type.startsWith("image/")) {
            this.$message.error("data項目の画像フォーマットエラー。")
            return
          }
        }

        // 检查上传的图片文件是否和itemList里面要求的图片文件一一对应

        //匹配成功的image_file,其实也就只是把itemList中的所有image_file提取出来
        //用set数量对比list的数量，得出是否有重复的image_file
        //或者也可以把下面isFileNameMatch改成计数大于1，来判断
        let matchedImgSet = new Set()

        // 遍历所有item对应的图片是否已上传
        for (let item of this.itemList) {
          var isFileNameMatch = false
          for (let img of this.imageList) {
            if (img.fileName == item.image_file) {
              isFileNameMatch = true

              // 匹配，加入封装？
              item["image"] = img.dataUrl

              break
            }
          }
          if (!isFileNameMatch) {
            this.$message.warning("CSVに指定した画像ファイルが不足です。")
            console.log(item.file_name + "がアップロードされていません。")
            return
          } else {
            matchedImgSet.add(item.image_file)
          }
        }

        // 检查是否有重复的图片文件
        console.log("matchedImgSet.size", matchedImgSet.size)
        console.log("itemList.length", this.itemList.length)
        if (this.itemList.length != matchedImgSet.size) {
          this.$message.error("画像をチェック成功しましたが、ファイルの記載されたデータは、複数の商品が同じ画像を使用しています。現在の画像を引き続き使用しますか。")
        } else {
          //必须先csv上传check以后，才能追加图片检查
          this.$message.success("画像をチェック成功しました。")
        }

        this.active = 4
      } catch (e) {
        console.log("error", e)
        this.$message.error("画像をチェック失敗しました。")
      }
    },
    removeImg(index) {
      console.log(index)
      this.imageList.splice(index, 1)
      console.log("imageList", this.imageList)
    },
    submit() {
      console.log("itemList", this.itemList)

      let _ = "http://localhost:8000"

      axios
        .post(_ + "/item", this.itemList)
        .then((res) => {
          console.log("res", res.data)
          console.log("this._data", this._data)

          if (res.data.status == 200) {
            this.$message.success("一括登録成功しました。")
            this.reset()
          } else {
            this.$message.error("一括登録失敗しました。")
          }
        })
        .catch((e) => {
          this.$message.error("一括登録失敗しました。")
          console.log(e)
        })
    },

    test() {
      let _ = "http://localhost:8000"

      axios.get(_ + "/item/hello").then((res) => {
        console.log("res", res.data)
      })

      axios.get(_ + "/item/image").then((res) => {
        console.log("res", res.data)
        this.testData.imgList = res.data.data
      })
    },
  },
}
</script>

<style scoped>
/*  */
div.upload {
  text-align: left;
}
hr.green-line {
  border: none;
  height: 1px;
  background-color: #6eecb4;
}
hr{
  border: none;
  height: 1px;
  background-color: rgb(165, 161, 161);
}

div.file-select {
  display: flex;
  gap: 15px;
  margin: 30px;
}
div.file-name {
  flex-grow: 1;
}
/*  */
div.button-area {
  text-align: left;
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 10px;
}

div.button-area button {
  color: white;
}

div.submit-reset {
  display: flex;
}

#submit-btn {
  background-color: #6bae91;
  flex-grow: 1;
}
#check-btn {
  background-color: rgb(92, 159, 221);
}
#reset-btn {
  background-color: orange;
}

/*  */
div.upload-image {
  text-align: left;
}

#add-image-btn {
  background-color: white;
  color: #6bae91;
  border: 2px #6bae91 solid;
}

ul {
  list-style-type: none;
  display: flex;
  flex-direction: row;
  gap: 35px;
}

/* li:hover #delete { */
#delete {
  display: flex;
  flex-direction: row-reverse;
}

/* #delete {
  display: none;
} */

#delete i {
  position: absolute;
  cursor: pointer;
}
</style>