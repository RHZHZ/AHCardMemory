<template>
	<div class="box">
		<div v-if="list.length===0">加载十八</div>
		<uv-line-progress :percentage="pocess" activeColor="#000000"></uv-line-progress>
		<div class="card">
			<div class="imgs" v-if="list[current]">
				<image class="images" :src="list[current].image" mode="aspectFill"></image>
				<div class="tips">
					<div class="theme">
						<uv-read-more show-height="200rpx" :toggle="true" style="position: absolute;">
							<view class="contents">
								
									<p>{{list[current].content}}</p>
								
							</view>
						</uv-read-more>
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
			<uv-toast ref="toast"></uv-toast>
		</div>
	</div>
</template>
<script>
	export default {
		data() {
			return {
				pocess: 0,
				showtips: true,
				memoryover: false,
				current: 0,
				mode: 'round',
				backgroudImg: '',
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
			const categorys = this.$route.query.category;
			console.log("Received item category:", categorys);

			// 向 API 接口发起请求  
			uni.request({
				url: 'http://localhost:8081/card/search',
				data: JSON.stringify({ // 使用 JSON.stringify() 序列化对象  
					category: categorys // 确保这里的值是您想要的字符串  
				}),
				method: 'POST',
				header: {
					'Content-Type': 'application/json' // 设置请求头为 application/json  
				},
				success: (res) => {
					console.log(categorys);
					this.list = res.data;
					console.log("sdasweqweqweqeqeqwewq" + this.list);
					this.$forceUpdate();
				},
				fail: (err) => {
					console.log(err);
				}
			});
			// uni.request({
			// 	url:"https://api.vvhan.com/api/wallpaper/views?type=json"
			// ,
			// success: (res) => {
			//   console.log(res);  
			//   this.backgroudImg = res.url;  
			//   console.log("sdasweqweqweqeqeqwewq" + this.backgroudImg);  
			// },  
			// fail: (err) => {  
			//   console.log(err);  
			// }} 
			// );
		},
		methods: {
			change(e) {
				this.current = e.detail.current;
			},
			remembered(index) {
				this.list[index].remembered = true;
				uni.showToast({
					title: '你记住啦！',
					icon: 'success'
				});
				this.scheduleNextReview();
			},
			forgotten(index) {
				this.list[index].remembered = false;
				uni.showToast({
					title: '继续努力！',
					icon: 'fail'
				});
				this.scheduleNextReview();
			},
			scheduleNextReview() {
				this.current += 1;
				this.currentIndex = this.current + 1;
				this.pocess = (this.current / this.list.length) * 100;
				console.log("this" + this.current + "next" + this.currentIndex + "总共" + this.list.length)
				//以下逻辑搬到后端
				//   if (this.currentIndex < this.list.length) {  
				//       // 如果还有未复习的卡片，则继续下一个  
				//       // 注意：this.current 的用途需要您确认，可能不需要这行代码  
				//       // this.current += 1;  

				//       // 获取当前卡片的复习信息  
				//       const currentCard = this.list[this.currentIndex];  
				//       const lastReviewDay = currentCard.lastReviewDay; // 假设 lastReviewDay 是上次复习的天数（整数）  

				//       // 根据艾宾浩斯遗忘曲线计算下一次复习的时间  
				//       let nextReviewDay;  
				//       if (currentCard.remembered) {  
				//           // 如果记得很好，延长复习间隔  
				//           // 假设延长复习间隔为原来的两倍天数  
				//           nextReviewDay = lastReviewDay * 2;  
				//       } else {  
				//           // 如果遗忘了，缩短复习间隔  
				//           // 假设缩短复习间隔为1天后  
				//           nextReviewDay =  1;  
				//       }  

				//       // 更新当前卡片的下一次复习时间  
				//       this.list[this.currentIndex].chooseday = nextReviewDay;  
				// this.list[this.currentIndex].lastReviewDay = nextReviewDay;

				//       // 打印或记录下一次复习时间  
				//       console.log("下次复习："+this.list[this.currentIndex].lastReviewDay);  

				//       // 根据具体情况进行其他操作，比如更新数据库中的复习计划等  
				//       // ...  
				//   }else{
				if (this.currentIndex > this.list.length) {
					uni.request({
						url: 'http://localhost:8081/card/updatecard',
						method: 'POST',
						header: {
							'content-type': 'application/json'
						},
						data: JSON.stringify(this.list),
						success: (res) => {
							if (res.data && res.data === 'Cards updated successfully') {
								uni.showToast({
									title: '卡片更新成功',
									icon: 'success'
								});

								this.list = [];
								this.current = 0,
									this.currentIndex = 0;
								console.log(this.list);
								console.log(this.current);
								console.log(this.currentIndex);
								setTimeout(() => {
									uni.navigateTo({
										url: '/pages/memory/memory'
									});
								}, 1000); // 在这里设置延迟的时间，单位为毫秒
							} else {
								uni.showToast({
									title: '卡片更新失败',
									icon: 'none'
								});
							}
						},
						fail: (error) => {
							uni.showToast({
								title: '请求失败，请检查网络',
								icon: 'none'
							});
						}
					});
				}
			}

		}
	}
</script>
<style lang="less">
	.box {
		height: 100vh;
		max-height: 100vh;

		background-color: #ffffff;
		background-size: cover;
		background-position: center;
		background-repeat: no-repeat;
	}

	.card {
		position: relative;
	}

	.imgs {
		position: relative;

	}

	.images {
		position: absolute;
		height: 100vh;
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
		text-align: justify;
		
	}

	.contents {
		width: 100vw;
		color: #55aaff;
		// padding: 0 30rpx;
		font-weight: bold;
		font-size: 20px;
		text-align: center;
	}

	

	.tag-button {
		display: flex;
		position: absolute;
		width: 250px;
		top: 70vh;
		left: 15vw;
		color: white;
		padding: 22px;

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
		// width: 50px;
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