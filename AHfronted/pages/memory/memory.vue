<template>
	
  <!-- <uni-card :isFull="true" class="container"> -->
  
	  <div class="container" >
		  <uv-sticky>
			  <view>
			  		<uv-notice-bar :text="hedertext" mode="closable" color="white" :bg-color="hedertextcolor"></uv-notice-bar>
			  	</view>
		  </uv-sticky>
		  <uni-card :is-full="true" style="border-radius: 34rpx;margin: 10rpx;" title=".">
			  <view class="plan-glue">
			  					<uni-tag   text="考研倒计时" type="success" size="default"/>
			  </view>
		  <uni-countdown :font-size="30" :day="1" :hour="2" :minute="30" :second="0" color="#FFFFFF" background-color="#ff0000" /></uni-card>
		  <uni-card  v-if="poemtext.origin" :is-full="true" style="border-radius: 34rpx; margin: 10rpx;" title="." :extra="poemtext.origin.dynasty + poemtext.origin.author">
		      <view class="plan-glue">
		          <text>{{ poemtext.origin.title }}</text>
		      </view>
		  
		      <uv-read-more show-height="200rpx" :toggle="true">
		          <view class="pom-content">
		              <view v-for="(item, index) in poemtext.origin.content" :key="index">
		                  <view>{{ item }}</view>
		              </view>
		  
<!-- 		              <div class="pom-sort">
		                  <div v-for="(item, index) in poemtext.matchTags" :key="index">
		                      <uni-tag :text="item" plain size="mini" type="default" plainFill></uni-tag>
		                  </div>
		              </div> -->
		          </view>
		      </uv-read-more>
		  </uni-card>
		
	<uni-card :is-full="true" style="border-radius: 34rpx;margin: 10rpx;" title=".">
		<view class="plan-glue">
							<uni-tag   text="学习计划" type="warning" size="default"/>
		</view>
		<swiper :indicator-dots="true" class="swiper" style="height: 12vh;">
		    <swiper-item v-for="(item, index) in contentList" :key="index">
		        <div class="content-item" @click="handleItemClick(item.id)">
		            <uv-row customStyle="margin-bottom: 10px">
		                <uv-col span="6">
		                    <view class="bg-purple-lights">
		                        <view>
		                            <p>{{ item.category }}</p>
		                        </view>
		                    </view>
		                </uv-col>
		                <uv-col span="6">
		                    <view class="bg-purple-lights">
		                        <view>开始时间: {{ item.startTime }}</view>
		                    </view>
		                </uv-col>
		            </uv-row>
		        </div>
		    </swiper-item>
		</swiper>
			
			
		<div class="content-item" v-for="(item, index) in contentList" :key="index">
					<uv-row customStyle="margin-bottom: 10px">
							<uv-col span="6">
								<view class="bg-purple-lights">
									<view>
										<p>{{ item.category }}</p>
									 </view>
									
									
								</view>
							</uv-col>
							<uv-col span="6">
								<view class="bg-purple-lights ">
									<view> 开始时间: {{ item.startTime }} </view>
								</view>
							</uv-col>
					</uv-row>
		</div>
	</uni-card>
	<!-- <button @click="addContent">添加</button> -->
	<!-- <uni-card :is-full="true" style="border-radius: 34rpx;" title="."> -->
		<uv-popup ref="popup" mode="center" round="24rpx" custom-style="width:100vw" :closeable=true>
					<view class="content">
					<!-- 添加自定义学习内容的表单 -->
					<uni-section title="添加卡片" type="line">
								<view class="examples">
									<!-- 基础用法，不包含校验规则 -->
									<uni-forms ref="baseForm" :modelValue="baseFormData">
										<uni-forms-item label="分类" required>
											<uni-easyinput v-model="baseFormData.name" placeholder="请输入标签分类" />
										</uni-forms-item>
										
										<uni-forms-item label="标签内容">
											<uni-easyinput type="textarea" v-model="newContent.title" placeholder="请输入标签内容" />
										</uni-forms-item>
										<uni-forms-item label="日期时间">
											<uni-datetime-picker type="datetime" return-type="timestamp" v-model="newContent.startTime"/>
										</uni-forms-item>
									</uni-forms>
									<!-- <uv-button type="info" text="添加字段" customStyle="margin-top: 10px" @click="add"></uv-button> -->
												<uv-button type="primary" text="提交" customStyle="margin-top: 10px" @click="submit"></uv-button>
												<uv-button type="error" text="重置" customStyle="margin-top: 10px" @click="reset"></uv-button>
								</view>
							</uni-section>
					
					</view>
		</uv-popup>
		
		<!-- 底部悬浮按钮 -->
		<uni-fab ref="fab" :pattern="pattern" :horizontal="horizontal" :vertical="vertical"
					:direction="direction" @trigger="trigger" @fabClick="addContent" />
	<!-- </uni-card> -->
	
	</div>
</template>

<script>
export default {
  data() {
    return {
      hedertextcolor: '#f56c6c',
      hedertext: '加油朋友！',
      baseFormData: {
        name: '',
        age: '',
        introduction: '',
        sex: 2,
        hobby: [5],
        datetimesingle: 1627529992399
      },
      pattern: {
        color: '#000000',
        backgroundColor: '#fff',
        selectedColor: '#007AFF',
        buttonColor: '#ffffff',
        iconColor: '#000000'
      },
      direction: 'horizontal',
      vertical: 'bottom',
      horizontal: 'right',
      contentList: [{ id:'',startTime:'',image: 'https://cdn.zebraui.com/zebra-ui/images/swipe-demo/swipe1.jpg', category: '动物',content:'刚记过',remembered:'false',chooseday:'',lastReviewDate:'' },
				{ id:'',image: 'https://cdn.zebraui.com/zebra-ui/images/swipe-demo/swipe1.jpg',category: '历史概念',content:'刚记过',remembered:'false',chooseday:'',lastReviewDate:'' , startTime: '2024-03-24' }],
      poemtext: {},
      newContent: {
        category: '',
        startTime: ''
      }
    };
  },
 mounted() {
     uni.request({
         url: 'https://v2.jinrishici.com/sentence',
         data: {
             // text: 'uni.request'
         },
         header: {
             'X-User-Token': 'xBSeGdKQt+X76kgParQMjkgiyYSrCF5D'
         },
         success: (res) => {
             console.log(res.data);
             this.poemtext = res.data.data;
			
             console.log("sdas"+this.poemtext);
         },
         fail: (err) => {
             console.log(err);
         }
     });
 },
  onBackPress() {
		if (this.$refs.fab.isShow) {
			this.$refs.fab.close()
			return true
		}
		return false
  		},
  methods: {
    reset() {
      this.newContent.category = '';
      this.newContent.startTime = '';
    },
    handleItemClick(itemId) {
      this.$router.push({ path: '../index/index', query: { id: itemId } });
    },
    submit() {
      if (this.newContent.title && this.newContent.startTime) {
        const date = new Date(this.newContent.startTime);
        const formattedTime = `${date.getFullYear()}年${date.getMonth() + 1}月${date.getDate()}日 ${date.getHours()}:${date.getMinutes()}`;

        this.contentList.push({
          title: this.newContent.title,
          startTime: formattedTime
        });

        this.hedertextcolor = "#5ac725";
        this.hedertext = `恭喜少侠添加 ${this.newContent.title} 分类！从此人生开启飞升模式！`;

        this.$refs.popup.close();
      } else {
        uni.showToast({
          icon: 'error',
          title: '请填写完整信息'
        });
      }
    },
    addContent() {
      this.$refs.popup.open();
    },
    trigger(e) {
      console.log(e);
    },
    fabClick() {
      uni.showToast({
        title: '点击了悬浮按钮',
        icon: 'none'
      });
    }
  }
};
</script>

<style scoped>
.container {
  max-width: 100vw;
  margin: 0 auto;
  height: auto;
  width: 100vw;
  border-radius: 20rpx;
}
.plan-glue {
  position: absolute;
  top: 1vh;
  left: 3vw;
}
.examples {
  padding: 15px;
  background-color: #fff;
  border-radius: 24rpx;
  margin-bottom: 10rpx;
}
.bg-purple-light {
  background: #e5e9f2;
}
.content-item {
  border: 1px solid #ccc;
  border-radius: 24rpx;
  padding: 10px;
  margin-bottom: 10rpx;
}
</style>