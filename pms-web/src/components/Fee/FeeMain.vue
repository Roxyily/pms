<template>
  <div>
    <div>
      <div class="searchBox">
        <el-input v-model="residentName" placeholder="请输入名字" style="width: 200px"
                  @keyup.enter.native="loadPost"></el-input>
        <el-input v-model="feeItemName" placeholder="输入收费项目" style="width: 150px"
                  @keyup.enter.native="loadPost"></el-input>
        <el-date-picker type="date" placeholder="选择起始时间" v-model="startDate" style="width: 150px;"></el-date-picker>
        <el-date-picker type="date" placeholder="选择结束时间" v-model="endDate" style="width: 150px;"></el-date-picker>

        <span class="box1">
           <el-button type="warning" plain  @click="loadPost">
            <i class="el-icon-search"></i>
          </el-button>
          <el-button type="warning" plain @click="resetParam">重置</el-button>
        </span>

        <span class="box3">
          <el-button type="warning" @click="downloadExcel">导出Excel</el-button>
        </span>

        <div class="box2">
          <el-button type="warning" plain @click="del">删除</el-button>
          <el-button  type="warning" plain style="margin-left: 5px" @click="add">新增</el-button>
        </div>
      </div>
    </div>

    <!--    数据表格-->
    <el-table :data="tableData"
              @selection-change="handleSelectionChange"
              :select-on-indeterminate=true
              :stripe=true
              :border=true
              :index-method="getIndex"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column type="index" >
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="communityId" label="小区号" >
      </el-table-column>
      <el-table-column prop="residentName" label="付款人" >
      </el-table-column>
      <el-table-column prop="contact" label="联系方式" >
      </el-table-column>
      <el-table-column prop="feeItemName" label="收费项目" >
      </el-table-column>
      <el-table-column prop="amount" label="收费金额（￥）" >
      </el-table-column>
      <el-table-column prop="feeDate" label="付款时间" width="250">
      </el-table-column>
      <el-table-column prop="operation" label="操作">
        <template slot-scope="scope">
          <i class="el-icon-edit" @click="editRow(scope.row)"></i>
        </template>
      </el-table-column>
    </el-table>

    <!--    分页管理器-->
    <div class="block">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page=pageNum
          :page-sizes="[5, 10, 20, 30]"
          :page-size=pageSize
          layout="total, sizes, prev, pager, next, jumper"
          :total=this.total>
      </el-pagination>
    </div>

    <!--    添加记录的表单-->
    <div>
      <el-dialog
          title="新增"
          :visible.sync="centerDialogVisible"
          width="30%"
          center
      >
        <el-form ref="form" :rules="rules" :model="form" label-width="80px">
          <el-form-item label="住户" prop="id">
            <el-col :span="20">
              <el-input v-model="form.residentId"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="付费项目" prop="fee-itemID">
            <el-col :span="20">
              <el-input v-model="form.feeItemId"></el-input>
            </el-col>
          </el-form-item>
          <el-row>
            <el-col :span="12">
              <el-form-item label="付款时间">
                <el-date-picker type="date" placeholder="选择日期" v-model="form.date" style="width: 100%;"></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="centerDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="save('update')">确 定</el-button>
        </span>
      </el-dialog>
    </div>

    <!-- 删除提示框 -->
    <el-dialog title="提示"
               :visible.sync="delVisible"
               width="300px"
               center
    >
      <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
      <el-button @click="delVisible = false">取 消</el-button>
      <el-button type="primary" @click="delAll">确 定</el-button>
    </span>
    </el-dialog>

    <!--编辑一条信息的表单-->
    <div>
      <el-dialog
          title="编辑信息"
          :visible.sync="editVisible"
          width="30%"
          center
      >
        <el-form ref="form" :rules="rules" :model="editForm" label-width="80px">
          <el-form-item label="收费项目" prop="feeItemName">
            <el-col :span="20">
              <el-input v-model="editForm.feeItemName">{{}}</el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="收费金额" prop="amount">
            <el-col :span="20">
              <el-input v-model="editForm.amount"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="付款时间" style="width: 167px">
            <el-date-picker type="date" placeholder="选择日期" v-model="editForm.feeDate" ></el-date-picker>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="editVisible = false">取 消</el-button>
          <el-button type="primary" @click="editConfirm =true">确 定</el-button>
        </span>
      </el-dialog>
    </div>

    <!-- 确定更改信息提示框 -->
    <el-dialog title="提示"
               :visible.sync="editConfirm"
               width="300px"
               center
    >
      <div class="del-dialog-cnt">是否更改这条信息?</div>
      <span slot="footer" class="dialog-footer">
      <el-button @click="editConfirm = false">取 消</el-button>
      <el-button type="primary" @click="save('edit')">确 定</el-button>
    </span>
    </el-dialog>
  </div>


</template>

<script>
import { mapState } from 'vuex'
export default {
  name: "HouseholdMain",
  data() {
    return {
      tableData: [],
      pageNum:1,
      pageSize:8,
      total:0,
      feeItemName:null,
      residentName:null,
      status:null,
      startDate:null,
      endDate:null,
      centerDialogVisible: false,
      form:{
        date: '',
        feeItemId: '',
        id: '',
        propertyId: '',
        residentId: '',
        status: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        contact: [
          { required: true, message: '联系方式不能空', trigger: 'blur' },
          { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: '请输入有效手机号', trigger: 'blur' }
        ],
        unitNumber: [
          { required: true, message: '请输入单元号', trigger: 'blur' },
          { min: 1, max: 2, message: '长度在 1 到 2 个字符', trigger: 'blur' }
        ],
        roomNumber: [
          { required: true, message: '请输入房间号', trigger: 'blur' },
          { min: 3, max: 4, message: '长度在 3 到 4 个字符', trigger: 'blur' }
        ]
      },
      delVisible:false,
      delID: [], // 存放删除的数据的id
      multipleSelection: [], // 多选的数据
      editVisible:false,
      editForm:{
        residentId:'',
        feeItemId:'',
        propertyId:'',
        feeDate:'',
        feeId:'',
        status:'',
      },
      editConfirm:false,
    }
  },
  computed:{
    ...mapState(['residentList'])
  },
  methods:{
    //导出Excel表
    downloadExcel(){
      this.$axios.post(this.$httpUrl + '/fee/exportAll', {
        param: {
          residentName:this.residentName,
          feeItemName:this.feeItemName,
          status:this.status,
          startDate:this.startDate,
          endDate:this.endDate,
        }
      }, {
        responseType: 'arraybuffer'
      }).then(res => {
        console.log(res.data);
        const a = document.createElement ('a')
        a.href = URL.createObjectURL (new Blob ([ res.data ], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }))
        a.download = '物业收费表.xlsx'
        a.click ()
      },).catch(error => {
        // 捕获错误，并进行相应的处理
        console.error(error);
        alert('下载失败');
      });
    },

    //每一页可显示的记录数更改时run
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum=1;
      this.pageSize=val;
      this.loadPost();
    },

    //分页时页数切换
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val;
      this.loadPost();
    },

    //表格记录的序号
    getIndex(index){
      return index + 1;
    },

    //重新加载表单
    resetFrom(){
      this.$refs.form.resetFields();
    },

    //多选操作
    handleSelectionChange(val){
      this.multipleSelection = val;
    },

    //批量删除
    del(){
      this.delVisible = true; // 显示删除弹框
      for(let i=0;i<this.multipleSelection.length;i++)
      {
        this.delID.push(this.multipleSelection[i].feeId)
      }
    },

    //确认删除
    delAll(){
      for(let i=0;i<this.delID.length;i++)
      {
        this.$axios.get(this.$httpUrl + '/fee/deleteFeeItem?id='+this.delID[i], ).then(res=>{
          console.log(res);
          console.log(res.status);
          if(res.status===200){
            console.log('删除成功');
            this.delVisible=false;
            this.$message({
              message: '删除成功！',
              type: 'success'
            });
            this.loadPost()
          }else{
            this.$message.error('删除失败！');
          }
        },)
      }
    },

    //添加记录
    doSave(){
      this.$axios.post(this.$httpUrl + '/fee/save',this.form).then(res=>{
        console.log(res);
        if(res.data.code===200){
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
          this.resetFrom()
        }else if(res.data.code===400){
          if(res.data.data==="User is not logged in"){
            this.$message({
              message: '操作失败！',
              type: 'error'
            });
          }
        }
      })
    },

    //修改记录
    doMod(){
      this.$axios.post(this.$httpUrl + '/fee/update',
          {
            date: this.editForm.feeDate,
            feeItemId: this.editForm.feeItemId,
            residentId:this.editForm.residentId,
            propertyId:this.editForm.propertyId,
            id:this.editForm.feeId,
            status:this.editForm.status,
          }).then(res=>{
        console.log(res);
        if(res.data.code===200){
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.editVisible = false
          this.editConfirm = false
          this.loadPost()
          this.resetFrom()
        }else if(res.data.code===400){
          if(res.data.data==="User is not logged in"){
            this.$message({
              message: '操作失败！',
              type: 'error'
            });
          }
        }
      })
    },

    //根据是添加记录还是修改记录提交不同的表单
    save(type){
      this.$refs.form.validate((valid) => {
        if (valid) {
          if(type==='edit'){
            this.doMod()
          }else if (type==='update'){
            this.doSave()
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      })
    },


    add(){
      this.centerDialogVisible = true;
      this.$nextTick(()=>{
        this.resetFrom()
      })
    },

    resetParam(){
      this.feeItemName=null;
      this.residentName=null;
      this.startDate=null;
      this.endDate=null;
    },

    editRow(row){
      this.editForm = Object.assign({}, row); // 将当前行的数据复制给表单的数据
      this.editVisible = true; // 显示修改表单
    },

    //加载列表
    loadPost(){
      this.$axios.post(this.$httpUrl + '/fee/listPage', {
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          residentName:this.residentName,
          feeItemName:this.feeItemName,
          status:this.status,
          startDate:this.startDate,
          endDate:this.endDate,
        }
      }).then(res=>{
        console.log(res.data);
        if(res.data.code===200){
          this.tableData=res.data.data;
          this.total=res.data.total;
        }else{
          alert('获取数据失败');
        }
      },)
    },
  },

  beforeMount() {
    this.loadPost();
  }
}
</script>

<style scoped>
.block {
  display: flex;
  justify-content: center;
  margin-top: 50px;
}

.searchBox {
  margin-top: 15px;
}
.searchBox *{
  margin: 0px 6px;
}

.searchBox el-input{
  border-radius: 0px;
}
.box1 {
  margin-left: 10px;
}

.box2 {
  margin-top: 15px;
  margin-left: 5px;
}

.el-icon-edit {
  margin-right: 6px;
  font-size: 20px;
}

.el-icon-edit:hover {
  color: #ffd04b;
}

.box3 {
  float: right;
}
</style>