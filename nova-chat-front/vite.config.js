import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import { fileURLToPath, URL } from 'node:url'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  resolve: {
    alias: {
      // 使用别名路径联想设置，需要心爱从node:url中导入fileURLToPath URL
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
    port: 5600,
    host: 'localhost',
    open: true,
    proxy: {
      '/nova-chat': {
        target: 'http://localhost:5700',
        changeOrigin: true
      }
    }
  }
})
