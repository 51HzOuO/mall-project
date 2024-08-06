import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
    server: {
        port: 8088,
        // proxy: {
        //     '/api': {
        //         target: 'http://localhost:8080', changeOrigin: true, rewrite: (path) => {
        //             return path.replace(/^\/api/, '')
        //         }
        //     }
        // }
    }, plugins: [vue(),], resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        }
    }
})
