import { defineConfig } from 'vite'
import { devtools } from '@tanstack/devtools-vite';
// @ts-ignore
import { heyApiPlugin} from "@hey-api/openapi-ts";

import { tanstackStart } from '@tanstack/react-start/plugin/vite'

import viteReact from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'
import netlify from '@netlify/vite-plugin-tanstack-start'
import contentCollections from '@content-collections/vite'

const config = defineConfig({
  resolve: { tsconfigPaths: true },
  plugins: [
    devtools(),
    netlify(),
    contentCollections(),
    tailwindcss(),
    tanstackStart(),
    viteReact(),
      heyApiPlugin({
        config: {
          input: "openApi.json",
          output: "src/generated"
        }
      })
  ],
})

export default config
