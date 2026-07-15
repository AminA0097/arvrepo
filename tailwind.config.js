/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        "./src/main/resources/templates/**/*.html",
        "./src/main/resources/static/**/*.js"
    ],
    theme: {
        extend: {
            fontFamily: {
                // Add your custom font here
                'text-font': ['Shabnam', 'system-ui', 'sans-serif'],
                'num-font': ['Tanha-FD', 'serif']
            },
            colors: {
                leather: {
                    50: '#F8F5F1',
                    100: '#F1E9E1',
                    200: '#E7DED5',
                    300: '#D6C2AE',
                    400: '#C8A97E',
                    500: '#A8794E',
                    600: '#8B5E3C',
                    700: '#6F472B',
                    800: '#53351F',
                    900: '#2F1E12'
                }
            },
            boxShadow: {
                soft: '0 10px 30px rgba(31, 26, 23, 0.08)',
                card: '0 8px 24px rgba(111, 71, 43, 0.12)'
            },
            borderRadius: {
                '2xl': '1rem'
            }
        }
    },
    plugins: [],
}