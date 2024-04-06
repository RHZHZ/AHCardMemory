<template>

	<!-- <uni-card :isFull="true" class="container"> -->
	<!-- <uv-skeleton rows="18" :loading="loading" :animate="true"> -->
	<div class="container">


		<uv-sticky>
			<view>
				<uv-notice-bar :text="hedertext" mode="closable" color="white"
					:bg-color="hedertextcolor"></uv-notice-bar>
			</view>
		</uv-sticky>
		<uni-card :is-full="true" style="border-radius: 34rpx; margin: 10rpx;" title=".">
			<view class="plan-glue">
				<uni-tag :text="planTime.planName" type="success" size="default" />
			</view>
			<uni-countdown :font-size="30" :day="planTime.customDay" :hour="planTime.customHour"
				:minute="planTime.customMinute" :second="planTime.customSecond" color="#FFFFFF"
				background-color="#ff0000" style="margin-left: 10vw;"/>
			<view class="plan-glue-button">
				<uv-button @click="diyPlan" size="default" shape="circle" color="#ffaa00">自定义计划</uv-button>

			</view>

		</uni-card>
		<div v-if="poem">
			<uni-card v-if="poemtext.origin" :is-full="true" style="border-radius: 34rpx; margin: 10rpx;" title="."
				:extra="poemtext.origin.dynasty + poemtext.origin.author">
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
		</div>

		<uni-card v-if="poemtext.zh" :is-full="true" style="border-radius: 34rpx; margin: 10rpx;" title=".">
			<view class="plan-glue">
				<uni-tag text="每日一句" type="error" size="default" />

			</view>

			<uv-read-more show-height="200rpx" :toggle="true">
				<view class="pom-content">
					<!-- <view v-for="(item, index) in poemtext" :key="index">
		  		                  <view>{{ item }}</view>
		  		              </view> -->
					<view>{{ poemtext.en }}</view>
					<view>{{ poemtext.zh }}</view>
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
				<uni-tag text="学习计划" type="warning" size="default" />
			</view>
			<swiper :indicator-dots="true" class="swiper" style="height: 12vh;">
				<swiper-item v-for="(item, index) in contentList" :key="index">
					<div class="content-item" @click="handleItemClick(item.category)">
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


			<div class="content-list">
				<div class="content-item" v-for="(item, index) in contentList" :key="index">
					<div class="swipe-delete">
						<div class="content-wrapper">
							<!-- 这里放置你原来的内容 -->
							<uv-row customStyle="margin-bottom: 10px">
								<uv-col span="6">
									<view class="bg-purple-lights">
										<view>
											<p @click="handleItemClick(item.category)">{{ item.category }}</p>

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
						<div class="delete-button" @click="deleteItem(item.id,index)">删除</div>
					</div>
				</div>
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
						<uni-forms ref="baseForm" :modelValue="newContent">
							<uni-forms-item label="分类" required>
								<uni-easyinput v-model="newContent.category" placeholder="请输入标签分类" />
							</uni-forms-item>

							<uni-forms-item label="标签内容">
								<uni-easyinput type="textarea" v-model="newContent.content" placeholder="请输入标签内容" />
							</uni-forms-item>
							<uni-forms-item label="日期时间">
								<uni-datetime-picker type="datetime" return-type="timestamp"
									v-model="newContent.startTime" />
							</uni-forms-item>
						</uni-forms>
						<!-- <uv-button type="info" text="添加字段" customStyle="margin-top: 10px" @click="add"></uv-button> -->
						<uv-button type="primary" text="提交" customStyle="margin-top: 10px" @click="submit"></uv-button>
						<uv-button type="error" text="重置" customStyle="margin-top: 10px" @click="reset"></uv-button>
					</view>
				</uni-section>

			</view>
		</uv-popup>
		<uv-popup ref="planpopup" mode="center" round="24rpx" custom-style="width:100vw" :closeable=true>
			<view class="content">
				<!-- 添加自定义学习内容的表单 -->
				<uni-section title="添加目标" type="line">
					<view class="examples">
						<!-- 基础用法，不包含校验规则 -->
						<uni-forms :modelValue="planDom">


							<uni-forms-item label="计划名称">
								<uni-easyinput type="textarea" v-model="planDom.planName" placeholder="请输入标签内容" />
							</uni-forms-item>
							<uni-forms-item label="日期时间">
								<uni-datetime-picker type="datetime" return-type="timestamp"
									v-model="planDom.planTime" />
							</uni-forms-item>
						</uni-forms>
						<!-- <uv-button type="info" text="添加字段" customStyle="margin-top: 10px" @click="add"></uv-button> -->
						<uv-button type="primary" text="提交" customStyle="margin-top: 10px"
							@click="plansubmit"></uv-button>
						<!-- <uv-button type="error" text="重置" customStyle="margin-top: 10px" @click="planreset"></uv-button> -->
					</view>
				</uni-section>

			</view>
		</uv-popup>
		<!-- 底部悬浮按钮 -->
		<uni-fab ref="fab" :pattern="pattern" :horizontal="horizontal" :vertical="vertical" :direction="direction"
			@trigger="trigger" @fabClick="addContent" />
		<!-- </uni-card> -->

	</div>
	<!-- </uv-skeleton> -->
</template>

<script>
	export default {
		data() {
			return {
				loading: true,
				poem: false,
				hedertextcolor: '#f56c6c',
				hedertext: '加油朋友！',
				pattern: {
					color: '#000000',
					backgroundColor: '#fff',
					selectedColor: '#007AFF',
					buttonColor: '#ffffff',
					iconColor: '#000000'
				},
				planTime: {
					planName: '先设置目标！',
					customDay: 0,
					customHour: 0,
					customMinute: 0,
					customSecond: 0
				},
				direction: 'horizontal',
				vertical: 'bottom',
				horizontal: 'right',
				contentList: [],
				poemtext: {},
				newContent: {
					category: '',
					chooseday: 1,
					lastReview: 1,
					content: '',
					startTime: null,

				},
				planDom: {
					planName: '',
					planTime: ''
				},
			};
		},
		onLoad() {
			setTimeout(() => {
				this.loading = false
			}, 2000)
		},
		mounted() {
			//   uni.request({
			//       url: 'https://v2.jinrishici.com/sentence',
			//       data: {
			//           // text: 'uni.request'
			//       },
			//       header: {
			//           'X-User-Token': 'xBSeGdKQt+X76kgParQMjkgiyYSrCF5D'
			//       },
			//       success: (res) => {
			//           console.log(res.data);
			//           this.poemtext = res.data.data;

			//           console.log("sdas"+this.poemtext);
			//       },
			//       fail: (err) => {
			//           console.log(err);
			//       }
			//   });
			//加载计划

			uni.request({
				url: 'http://localhost:8081/plan/getPlan',
				success: (res) => {
					this.planTime.planName = res.data.planName;
					console.log(res.data);

					const planTime = res.data.planTime;
					const convertedTime = this.convertTime(planTime);

					this.planTime.customDay = convertedTime.customDay;
					this.planTime.customHour = convertedTime.customHour;
					this.planTime.customMinute = convertedTime.customMinute;
					this.planTime.customSecond = convertedTime.customSecond;

					console.log(this.planTime);
				},
				fail: (err) => {
					console.log(err);
				}
			});



			//每日一句英语
			uni.request({
				url: 'https://api.vvhan.com/api/dailyEnglish?type=sj',
				data: {
					// text: 'uni.request'
				},
				header: {
					'X-User-Token': 'xBSeGdKQt+X76kgParQMjkgiyYSrCF5D'
				},
				success: (res) => {
					console.log(res.data);
					this.poemtext = res.data.data;

					console.log("sdas" + this.poemtext.en);
				},
				fail: (err) => {
					console.log(err);
				}
			});
			// //加载学习计划
			// uni.request({
			//     url: 'https://v2.jinrishici.com/sentence',
			//     data: {
			//         // text: 'uni.request'
			//     },
			//     header: {
			//         'X-User-Token': 'xBSeGdKQt+X76kgParQMjkgiyYSrCF5D'
			//     },
			//     success: (res) => {
			//         console.log(res.data);
			//         this.poemtext = res.data.data;

			//         console.log("sdas"+this.poemtext);
			//     },
			//     fail: (err) => {
			//         console.log(err);
			//     }
			// });
			// 加载卡片数据
			uni.request({
				url: 'http://localhost:8081/card/getAll',
				success: (res) => {
					console.log(res.data);
					this.contentList = res.data;

					// 将startTime字符串转换为数字，再转换为具体时间格式
					this.contentList.forEach(item => {
						const startTimeTimestamp = parseInt(item.startTime); // 将字符串转换为数字
						item.startTime = new Date(startTimeTimestamp).toLocaleString(); // 转换为具体时间格式
					});

					console.log(this.contentList);
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
			convertTime: function(planTime) {
				const nowTime = new Date().getTime(); // 获取当前时间的时间戳

				// 计算时间差
				const timeDiff = planTime - nowTime;

				// 计算相差的天数、小时数、分钟数和秒数
				const customDay = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
				const customHour = Math.floor((timeDiff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
				const customMinute = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60));
				const customSecond = Math.floor((timeDiff % (1000 * 60)) / 1000);

				return {
					customDay: customDay,
					customHour: customHour,
					customMinute: customMinute,
					customSecond: customSecond
				};
			},
			plansubmit() {
				console.log(this);
				console.log("this.planDom.planTime" + this.planDom.planTime);
				console.log("this.planDom.planName" + this.planDom.planName);
				if (this.planDom.planTime && this.planDom.planName) {
					// 发起POST请求到接口/localhost:8081/plan/add  
					uni.request({
						url: 'http://localhost:8081/plan/add',
						method: 'POST',
						data: this.planDom,
						success: (res) => {
							console.log(res)
							if (res.data == "successfully") {
								// 请求成功，处理响应数据  
								console.log(res.data)
								this.hedertextcolor = "#ff5500";
								this.hedertext = `恭喜新的计划 ${this.planDom.planName} 开始！要加油哦！`;
								//更新本地数据-vue响应特性，免刷新
								this.planTime.planName = this.planDom.planName;
								// 将startTime字符串转换为数字，再转换为具体时间格式
								const convertedTime = this.convertTime(this.planDom.planTime);
								this.planTime.customDay = convertedTime.customDay;
								this.planTime.customHour = convertedTime.customHour;
								this.planTime.customMinute = convertedTime.customMinute;
								this.planTime.customSecond = convertedTime.customSecond;

								console.log(this.planTime);
								this.$refs.planpopup.close();
								// 清除表单数据或重置表单  
								this.planDom = {

								};

								this.$forceUpdate();

							} else {
								// 请求失败，显示错误信息  
								uni.showToast({
									icon: 'error',
									title: res.data.message || '添加失败'
								});
							}
						},
						fail: (err) => {
							// 请求失败，处理错误  
							uni.showToast({
								icon: 'error',
								title: '网络错误，请稍后再试'
							});
							console.error(err);
						}
					});
				} else {
					// 表单未填完整，显示提示信息  
					uni.showToast({
						icon: 'error',
						title: '请填写完整信息'
					});
				}
				this.$forceUpdate();
			},
			reset() {
				this.newContent.category = '';
				this.newContent.startTime = '';
			},
			handleItemClick(category) {
				this.$router.push({
					path: '../index/index',
					query: {
						category: category
					}
				});

			},
			submit() {
				if (this.newContent.category && this.newContent.startTime) {
					const date = new Date(this.newContent.startTime);
					const formattedTime =
						`${date.getFullYear()}年${date.getMonth() + 1}月${date.getDate()}日 ${date.getHours()}:${date.getMinutes()}`;

					this.contentList.push({
						category: this.newContent.category,
						startTime: formattedTime
					});

					// 发起POST请求到接口/localhost:8081/card/add  
					uni.request({
						url: 'http://localhost:8081/card/add',
						method: 'POST',
						data: this.newContent,
						success: (res) => {
							console.log(res)
							if (res.data == "成功") {
								// 请求成功，处理响应数据  
								console.log(res.data)


								this.hedertextcolor = "#5ac725";
								this.hedertext = `恭喜少侠添加 ${this.newContent.category} 分类！从此人生开启飞升模式！`;

								this.$refs.popup.close();
								// 清除表单数据或重置表单  
								this.newContent = {
									category: '',
									chooseday: 1,
									lastReview: 1,
									content: '',
									startTime: null
								};
							} else {
								// 请求失败，显示错误信息  
								uni.showToast({
									icon: 'error',
									title: res.data.message || '添加失败'
								});
							}
						},
						fail: (err) => {
							// 请求失败，处理错误  
							uni.showToast({
								icon: 'error',
								title: '网络错误，请稍后再试'
							});
							console.error(err);
						}
					});
				} else {
					// 表单未填完整，显示提示信息  
					uni.showToast({
						icon: 'error',
						title: '请填写完整信息'
					});
				}
			},
			addContent() {
				this.$refs.popup.open();
			},
			diyPlan() {
				this.$refs.planpopup.open();
			},
			deleteItem(id, index) {
				console.log("删除" + index)
				uni.request({
					data: {
						id: id // 直接发送id，而不是JSON对象  
					},
					url: 'http://localhost:8081/card/del',
					method: 'POST',
					header: {
						'Content-Type': 'application/x-www-form-urlencoded' // 如果发送的是表单数据，设置这个头部   // 设置请求头为 application/json  
					},
					success: (res) => {
						if (res.statusCode === 200) {
							// 请求成功，再在前端删除对应的 contentList 中的项
							this.contentList.splice(index, 1); // 删除指定索引的元素
							uni.showToast({
								title: '删除成功',
								icon: 'success'
							});

						} else {
							uni.showToast({
								title: '删除失败',
								icon: 'none'
							});
						}
					},
					fail: (err) => {
						console.error('请求异常', err);
						uni.showToast({
							title: '请求异常',
							icon: 'none'
						});
					}
				});
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
		height: 100vh;
		width: 100vw;
		border-radius: 20rpx;
		background-image: url(https://api.vvhan.com/api/wallpaper/views);
		background-size: cover;
		background-position: center;
		background-repeat: no-repeat;
	}

	.plan-glue-button {
		position: absolute;
		top: 0vh;
		right: 2vw;

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

	.swipe-delete {
		position: relative;
		overflow: hidden;
	}

	.delete-button {
		position: absolute;
		top: 22px;
		right: -100px;
		/* right: 0px; */
		/* 初始时隐藏删除按钮 */
		width: 100px;
		height: 40px;
		line-height: 100%;
		background-color: red;
		color: #fff;
		text-align: center;
		transition: right 0.3s ease;
		border-radius: 24px;
		/* 添加动画效果 */
	}

	.swipe-delete:hover .delete-button {
		right: 0;
		/* 鼠标悬停时显示删除按钮 */
	}
</style>