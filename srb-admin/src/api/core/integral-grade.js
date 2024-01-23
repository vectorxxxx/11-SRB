// @ 符号在vue.config.js 中配置， 表示 'src' 路径的别名
import request from '@/utils/request'

const api_name = '/admin/core/integralGrade'

export default {
  // 获取积分等级列表
  list() {
    return request({
      url: `${api_name}/list`,
      method: 'get'
    })
  },

  // 删除积分等级
  removeById(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: 'delete'
    })
  },

  // 添加积分等级
  save(integralGrade) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: integralGrade
    })
  },

  // 根据id获取积分等级
  getById(id) {
    return request({
      url: `${api_name}/get/${id}`,
      method: 'get'
    })
  },

  // 修改积分等级
  updateById(integralGrade) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: integralGrade
    })
  }
}
