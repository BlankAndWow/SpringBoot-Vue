module.exports = {
  // cli3 代理是从指定的target后面开始匹配的，不是任意位置；配置pathRewrite可以做替换
  devServer: {
    proxy: {
      '/api': {
        target: 'http://192.168.1.147:9090',
        changeOrigin: true
      }
    }
  }
}