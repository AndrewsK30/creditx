require('babel-polyfill');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const path = require('path');
const fs  = require('fs');
const webpack = require('webpack');
const host = (process.env.HOST || 'localhost');
const port = process.env.PORT || 3000;
const autoprefixer = require('autoprefixer');
const lessToJs = require('less-vars-to-js');
const themeVariables = lessToJs(fs.readFileSync(path.join(__dirname, './src/theme.less'), 'utf8'));
themeVariables["@icon-url"] = "'//localhost:3000/src/assets/fonts/iconfont'";
module.exports = {
    devtool: 'eval-source-map',
    entry: [
        'react-hot-loader/patch',
        'babel-polyfill',
        'webpack-dev-server/client?http://' + host + ':' + port,
        'webpack/hot/only-dev-server',
        './src/index',
    ],
    output: {
        path: path.join(__dirname, 'dist'),
        filename: 'bundle.js',
        publicPath: '/dist/',
        devtoolModuleFilenameTemplate: '/[absolute-resource-path]',
    },
    resolve: {
        extensions: ['.js', '.jsx'],
        modules: [path.resolve(__dirname, 'src'), path.resolve(__dirname, 'node_modules')],
    },
    module: {
        rules: [
            {
                test: /\.jsx?$/,
                loaders: ['babel-loader'],
                include: path.join(__dirname, 'src')
            },
            {
                test: /\.(png|jpg)$/,
                loader: 'url-loader?limit=8192&name=img/[name].[ext]'
            },
            {
                test: /\.(woff|woff2|eot|ttf|svg)(\?.*$|$)/,
                loader: 'file-loader?name=/src/assets/fonts/[name].[ext]'
            },
            {
                test: /\.css$/,
                loader: 'style-loader!css-loader'
            },
            {
                test: /\.less$/,
                use: [
                    {loader: "style-loader"},
                    {loader: "css-loader"},
                    {loader: "less-loader",
                        options: {
                            modifyVars: themeVariables
                        }
                    }
                ]
            }

        ]
    },
    externals: {
        'cheerio': 'window',
        'react/lib/ExecutionEnvironment': true,
        'react/lib/ReactContext': true,
    },
    plugins: [
        new webpack.LoaderOptionsPlugin({
            options: {
                eslint: {
                    failOnError: true,
                },
                context: '/',
                debug: true,
                postcss: [autoprefixer],
            },
        }),
        new webpack.HotModuleReplacementPlugin(),
        new webpack.DefinePlugin({
            'process.env': {
                'NODE_ENV': JSON.stringify('development'),
            }
        })
    ],
}
