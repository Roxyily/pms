<template>
   <div class="main">
        <div class="info">
            <el-descriptions title="个人信息" direction="vertical" :column="4" border>
            <el-descriptions-item label="用户名">{{ $store.getters.getUser.no }}</el-descriptions-item>
            <el-descriptions-item label="手机号">{{ $store.getters.getUser.phone }}</el-descriptions-item>
            <el-descriptions-item label="年龄" :span="2">{{ $store.getters.getUser.age }}</el-descriptions-item>
            <el-descriptions-item label="管理员编号" >{{ $store.getters.getUser.id }}</el-descriptions-item>
            <el-descriptions-item label="管理的小区编号">{{ $store.getters.getUser.communityId }}</el-descriptions-item>
            <el-descriptions-item label="性别">{{ $store.getters.getUser.sex ? '男' : '女'}}</el-descriptions-item>
            </el-descriptions>
        </div>

         <div class="account">
            <el-descriptions title="账号管理" direction="vertical" :column="4" border>
            <el-descriptions-item label="用户名">{{ $store.getters.getUser.no }}</el-descriptions-item>
            <el-descriptions-item label="账号">{{ $store.getters.getUser.name }}</el-descriptions-item>
            <el-descriptions-item label="密码" :span="2">
              <el-input :type="isVisable ? 'text' : 'password'" :disabled="!isEditable" v-model="password"></el-input>
              <el-button @click="enableEditing">修改</el-button>
              <el-button @click="update_pwd">保存</el-button>
            </el-descriptions-item>
            <el-descriptions-item label="权限">
                <el-tag size="small" :type="this.admin_name==='超级管理员' ? 'danger' : ''">
                  {{ this.admin_name==='超级管理员' ? '超级管理员':'管理员'}}
                </el-tag>
            </el-descriptions-item>
            </el-descriptions>
         </div>

         <div class="admin_table"  v-show="this.admin_name==='超级管理员'">
           <div class="title">管理员列表</div>

           <div class="box2">
             <el-button type="warning" plain @click="del">删除</el-button>
             <el-button  type="warning" plain style="margin-left: 5px" @click="add">新增</el-button>
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
             <el-table-column prop="no" label="姓名" width="200">
             </el-table-column>
             <el-table-column prop="name" label="账号名" width="200">
             </el-table-column>
             <el-table-column prop="communityId" label="管理的小区" width="200">
             </el-table-column>
             <el-table-column prop="phone" label="手机号" width="200">
             </el-table-column>
             <el-table-column prop="sex" label="性别" width="200">
               <template #default="scope">
                 {{scope.row.sex ? '男':'女'}}
               </template>
             </el-table-column>
<!--             <el-table-column prop="operation" label="操作" width="80">-->
<!--               <template slot-scope="scope">-->
<!--                 <i class="el-icon-edit" @click="editRow(scope.row)"></i>-->
<!--               </template>-->
<!--             </el-table-column>-->
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
        </div>

     <!--    添加记录的表单-->
     <div>
       <el-dialog
           title="新增"
           :visible.sync="centerDialogVisible"
           width="30%"
           center
       >
         <el-form ref="form"  :model="form" label-width="80px">
           <el-form-item label="姓名" prop="no">
             <el-col :span="20">
               <el-input v-model="form.no"></el-input>
             </el-col>
           </el-form-item>
           <el-form-item label="账号名" prop="name">
             <el-col :span="20">
               <el-input v-model="form.name"></el-input>
             </el-col>
           </el-form-item>
           <el-form-item label="密码" prop="password">
             <el-col :span="20">
               <el-input v-model="form.password"></el-input>
             </el-col>
           </el-form-item>
           <el-form-item label="联系方式" prop="phone">
             <el-col :span="20">
               <el-input v-model="form.phone"></el-input>
             </el-col>
           </el-form-item>
           <el-form-item label="管理小区" prop="communityId">
             <el-col :span="20">
               <el-input v-model="form.communityId"></el-input>
             </el-col>
           </el-form-item>
           <el-form-item label="性别" prop="sex">
             <el-col :span="20">
               <el-select v-model="form.sex" placeholder="请选择">
                 <el-option
                     v-for="item in options"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
                 </el-option>
               </el-select>
             </el-col>
           </el-form-item>
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
         <el-form ref="form" :model="editForm" label-width="80px">
           <el-form-item label="姓名" prop="no">
             <el-col :span="20">
               <el-input v-model="editForm.no">{{}}</el-input>
             </el-col>
           </el-form-item>
           <el-form-item label="账号名" prop="name">
             <el-col :span="20">
               <el-input v-model="editForm.name">{{}}</el-input>
             </el-col>
           </el-form-item>
           <el-form-item label="管理小区" prop="communityId">
             <el-col :span="20">
               <el-input v-model="editForm.communityId"></el-input>
             </el-col>
           </el-form-item>
           <el-form-item label="联系方式" prop="phone">
             <el-col :span="20">
               <el-input v-model="editForm.phone"></el-input>
             </el-col>
           </el-form-item>
           <el-form-item label="性别" prop="sex">
             <el-col :span="20">
               <el-select v-model="editForm.sex" placeholder="请选择">
                 <el-option
                     v-for="item in options"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
                 </el-option>
               </el-select>
             </el-col>
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
  export default {
    name: "IndexMain",
    data() {
      return {
        tableData: [],
        isEditable: false,
        isVisable: false,
        password: this.$store.getters.getUser.password,
        admin_name:this.$store.getters.getUser.name,
        pageNum: 1,
        pageSize: 8,
        total: 0,
        centerDialogVisible: false,
        name: '',
        form: {
          age: '',
          communityId: '',
          id: '',
          isvalid: '',
          name: '',
          no: '',
          password: '',
          phone: '',
          residentId: '',
          roleId: '',
          sex: ''
        },
        delVisible:false,
        delID: [], // 存放删除的数据的id
        multipleSelection: [], // 多选的数据
        status:null,
        options:[
          {
            value:'0',
            label:'女',
          },
          {
            value:'1',
            label:'男',
          },
        ],
        value: '',
        editVisible:false,
        editForm:{
          age: '',
          communityId: '',
          id: '',
          isvalid: '',
          name: '',
          no: '',
          password: '',
          phone: '',
          residentId: '',
          roleId: '',
          sex: ''
        },
        editConfirm:false,
      }
    },
    computed: {
      sexLabel() {
        return this.editForm.sex ? '男' : '女';
      }
    },
    methods: {
      enableEditing() {
        this.isVisable = true;
        this.isEditable = true;
      },
      update_pwd() {
        this.isVisable = false;
        this.isEditable = false;
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
        return  index + 1;
      },
      //多选操作
      handleSelectionChange(val){
        console.log(val)
        this.multipleSelection = val;
      },

      //批量删除
      del(){
        this.delVisible = true; // 显示删除弹框
        for(let i=0;i<this.multipleSelection.length;i++)
        {
          this.delID.push(this.multipleSelection[i].id)
        }
      },

      //确认删除
      delAll(){
        for(let i=0;i<this.delID.length;i++)
        {
          this.$axios.get(this.$httpUrl + '/user/delete?id='+this.delID[i], ).then(res=>{
            console.log(res);
            console.log(res.data.code);
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

      resetFrom(){
        this.$refs.form.resetFields();
      },
      //添加记录
      doSave(){
        this.$axios.post(this.$httpUrl + '/user/registerAdministrator',this.form).then(res=>{
          console.log(res);
          if(res.data.code===200){
            this.$message({
              message: '操作成功！',
              type: 'success'
            });
            this.centerDialogVisible = false
            this.loadPost()
            this.resetFrom()
          }
        })
      },

      //修改记录
      doMod(){
        this.$axios.post(this.$httpUrl + '/user/mod',this.editForm).then(res=>{
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
        this.name='';
        this.unitNumber='';
        this.roomNumber='';
      },


      editRow(row){
        this.editForm = Object.assign({}, row); // 将当前行的数据复制给表单的数据
        this.editVisible = true; // 显示修改表单
      },
      //加载列表
      loadPost() {
        this.$axios.post(this.$httpUrl + '/user/listPage', {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            name: this.name,
          }
        }).then(res => {
          console.log(res);
          console.log(res.data);
          if (res.status=== 200) {
            this.tableData = res.data;
            this.total = res.data.total;
          } else {
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
  .main {
    height: 700px;
    overflow: scroll;
  }
  .info {
    padding: 10px;
    background-color:rgb(244, 243, 243);
    border-radius: 0px;
    margin-top: 10px;
  }

  .account {
    padding: 10px;
    background-color:rgb(244, 243, 243);
    border-radius: 0px;
    margin-top: 25px;
  }

  .el-input {
    width: 250px;
  }

  .title {
    margin-left: 12px;
    margin-top: 25px;
    margin-bottom: 25px;
    font-weight: bold;
  }

  .block {
    display: flex;
    justify-content: center;
    margin-top: 50px;
  }

  .el-icon-edit {
    margin-right: 6px;
    font-size: 20px;
  }

  .el-icon-edit:hover {
    color: #ffd04b;
  }

  .box2 {
    margin-top: 15px;
    margin-left: 5px;
  }
  </style>