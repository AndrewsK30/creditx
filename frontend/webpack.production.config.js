require('babel-polyfill');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
var path = require('path');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const fs  = require('fs');
const CleanWebpackPlugin = require('clean-webpack-plugin');
var webpack = require('webpack');
var CompressionPlugin = require('compression-webpack-plugin');
const lessToJs = require('less-vars-to-js');
const themeVariables = lessToJs(fs.readFileSync(path.join(__dirname, './src/theme.less'), 'utf8'));
themeVariables["@icon-url"] = "'/assets/fonts/iconfont'";

const extractStyles = new ExtractTextPlugin({
    filename: 'styles-[hash].min.css',
    allChunks: true,
    disable: true
});

module.exports = {
    devtool: 'cheap-module-source-map',
    entry: './src/index',
    output: {
        path: path.join(__dirname, 'dist'),
        filename: 'bundle.js',
        publicPath: '/static/',
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
                loader: ExtractTextPlugin.extract({fallback:'style-loader', use:'css-loader'})
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
        new CleanWebpackPlugin(path.join(__dirname, 'dist')),
        new webpack.DefinePlugin({
            'process.env': {
                'NODE_ENV': JSON.stringify('production'),
            }
        }),
        extractStyles,
        new webpack.optimize.ModuleConcatenationPlugin(),
        new webpack.optimize.OccurrenceOrderPlugin(),
        new webpack.optimize.UglifyJsPlugin({
            compress: {
                warnings: false,
                screw_ie8: true,
                conditionals: true,
                unused: true,
                comparisons: true,
                sequences: true,
                dead_code: true,
                evaluate: true,
                if_return: true,
                join_vars: true
            },
            output: {
                comments: false
            }
        }),
        new CopyWebpackPlugin([{ from: 'src/assets',to: 'assets',type:'dir'},{ from: './index.html', to: 'index.html' }]),
        new webpack.optimize.AggressiveMergingPlugin(),
        new CompressionPlugin({
            asset: '[path].gz[query]',
            algorithm: 'gzip',
            test: /\.js$|\.css$|\.html$|\.eot?.+$|\.ttf?.+$|\.woff?.+$|\.svg?.+$/,
            threshold: 10240,
            minRatio: 0.8
        })
    ]
};
