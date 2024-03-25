<template>
  <div class="box">
    <div class="card">
      <div class="imgs" v-if="list[current]">
        <image class="images" :src="list[current].image" mode="aspectFill"></image>
        <div class="tips">
          <div class="theme">
            <uni-card style="position: absolute; border-radius: 24px; margin-left: 30vw; margin-right: auto;">
              <uv-text type="success" decoration="underline" lines="5" size="22" :bold="true" :text="list[current].content"></uv-text>
            </uni-card>
          </div>
        </div>
        <div class="tag">
          <text class="text">{{ list[current].category }}</text>
        </div>
        <div class="tag-button">
          <button @click="remembered(current)">记住了</button>
          <button @click="forgotten(current)">遗忘了</button>
        </div>
        <div class="footer">
          <view class="tag-view">
            <view class="tag-view">
              <uni-tag :text="list[current].content" type="warning" />
            </view>
          </view>
        </div>
      </div>
      <uv-notify ref="notify"></uv-notify>
      <uv-toast ref="toast"></uv-toast>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      memoryover: false,
      current: 0,
      mode: 'round',
      slideCustomStyle: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: '36rpx'
      },
      options: {
        effect: 'cards'
      },
      list: [],
      currentIndex: 0,
      params: {
        type: 'success',
        title: '结束后跳转标签页',
        message: "胜利终将属于我们！",
        url: '../memory/memory'
      }
    };
  },
mounted() {
  // 获取传递过来的item.id
  const itemId = this.$route.query.id;
  console.log("Received item id:", itemId);
  
  // 在这里可以根据itemId做进一步的处理
  // 向API接口发起请求
  this.$axios.get(`https://example.com/api/data?id=${itemId}`)
      .then(response => {
          // 请求成功，将返回的数据放到list中
          this.list = response.data;
		 
      })
      .catch(error => {
          // 请求失败，处理错误
          console.error('Error fetching data:', error);
		  this.list = [
		      { image: 'https://cdn.zebraui.com/zebra-ui/images/swipe-demo/swipe1.jpg', category: '动物',content:'刚记过',remembered:'false',chooseday:'',lastReviewDate:'' },
		      { image: 'https://cdn.zebraui.com/zebra-ui/images/swipe-demo/swipe2.jpg', category: '风景' ,content:'我是提示词',remembered:'false',chooseday:'',lastReviewDate:''},
		      { image: 'https://cdn.zebraui.com/zebra-ui/images/swipe-demo/swipe3.jpg', category: '建筑',content:'我是提示词' ,remembered:'false',chooseday:'',lastReviewDate:''},
		      { image: 'https://cdn.zebraui.com/zebra-ui/images/swipe-demo/swipe4.jpg', category: '人物' ,content:'我是提示词',remembered:'false',chooseday:'',lastReviewDate:''},
		      { image: 'https://cdn.zebraui.com/zebra-ui/images/swipe-demo/swipe5.jpg', category: '艺术' ,content:'我是提示词',remembered:'false',chooseday:'',lastReviewDate:''},
		    ];
		  			console.log(this.list);
      });
},
 methods: {
    change(e) {
      this.current = e.detail.current;
    },
    remembered(index) {
      this.list[index].remembered = true;
      this.scheduleNextReview();
    },
    forgotten(index) {
      this.list[index].remembered = false;
      this.scheduleNextReview();
    },
	scheduleNextReview() {
	    this.currentIndex++;
	
	    if (this.currentIndex < this.list.length) {
	        // 如果还有未复习的卡片，则继续下一个
	        this.current += 1;
	
	        // 模拟根据遗忘曲线调整复习计划的逻辑
	        let currentCard = this.list[this.currentIndex];
	        let lastReviewDate = currentCard.lastReviewDate;
	        let nextReviewDate;
	
	        // 根据艾宾浩斯遗忘曲线计算下一次复习的时间
	        if (currentCard.remembered) {
	            // 如果记得很好，延长复习间隔->2*before
	            nextReviewDate = lastReviewDate * 2 ; // 将秒转换为毫秒
	        } else {
	            // 如果遗忘了，缩短复习间隔->1天后
	            nextReviewDate = 1; // 将秒转换为毫秒
	        }
	
	        // 更新当前卡片的下一次复习时间
	        currentCard.nextReviewDate = nextReviewDate;
			 currentCard.chooseday = nextReviewDate;
	
	        // 可以根据具体情况进行其他操作，比如更新数据库中的复习计划等
	    } else {
	        // 所有卡片都已经复习完毨
	        // 可以给出提示或者其他操作
			this.$refs.toast.show({
					...this.params,
				complete() {
					uni.navigateTo({
						url:"/pages/memory/memory"
					})
				}
				})
						

	    }
	}
  }
  }

</script>
<style lang="less">
.box {
  height: 80vh;
  max-height: 100vh;
  background-color: #55aaff;
}

.card {
  position: relative;
}

.imgs {
  position: relative;
}

.images {
  position: absolute;
  height: 80vh;
  width: 100vw;
  top: 20px;
  // margin-left: 5vw;
  border-radius: 24rpx;
}

.tips {
  background-color: #55aaff;
  width: 100vw;
  position: absolute;
  top: 30vh;
  align-items: center;
  text-align: center;
}

.theme {
  // position: absolute;
  // top: 50%;
  // text-align: center;
  display: flex;
  // left: 50%;
  // transform: translate(-50%, -50%);
}
.tag-button {
  display: flex;
  position: absolute;
  width: 250px;
  top: 70vh;
  left: 20vw;
  color: white;
  padding: 2px;
  button {
    background: #ffffffff;
    width: 30vw;
    margin: 10px;
    border-radius: 34rpx;
  }
}

.footer {
  display: flex;
  position: absolute;
  top: 3vh;
  right: 2vw;
  
}

.tag {
  position: absolute;
  width: 50px;
  top: 20px;
  // left: 10px;
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  padding: 5px;
  border-radius: 25px;
  .text {
    margin-left: 10px;
  }
}
</style>