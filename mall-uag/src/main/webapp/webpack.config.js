var webpack = require('webpack');
var path = require('path');
var TransferWebpackPlugin = require('transfer-webpack-plugin');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require("extract-text-webpack-plugin");
var CleanWebpackPlugin = require('clean-webpack-plugin'); //清理文件夹
var nodeModulesPath = path.join(__dirname, 'node_modules');
var OptimizeCssAssetsPlugin = require('optimize-css-assets-webpack-plugin');
//webpack配置-根据开发模式可改变配置
var webpackConfig = {
    nameHash: ".[hash:8]",//hash规则
    config:path.resolve(__dirname,"./app/js/config.js"),//配置文件
    output: {//输出文件配置
        path: path.resolve(__dirname, './dist'), // 设置输出目录
        publicPath: "/", //静态文件目录，如果网站路径直接指到dist目录，请注意改为/
        filename: 'js/[name].[hash].js', // 输出文件名
        chunkFilename: 'js/[name].[hash].js', // 按需加载模块输出文件名
    },
    plugins: {
        define: new webpack.DefinePlugin({
            "process.env": {//上线后的devtool要配置为source-map,有时候为了性能考虑，一定要配置这个插件
                NODE_ENV: JSON.stringify("production")
            }
        }),
        clean: new CleanWebpackPlugin(['*'], {
            root: path.resolve(__dirname, './dist'),
            verbose: true,
            dry: false,
            exclude: []
        }),
        uglify: new webpack.optimize.UglifyJsPlugin({
            mangle: {
                except: ['$super', '$', 'exports', 'require']
                //以上变量‘$super’, ‘$’, ‘exports’ or ‘require’，不会被混淆
            },
            compress: {
                //supresses warnings, usually from module minification
                warnings: false
            }
        })
    }
};
//github打包模式
if (process.env.NODE_ENV === 'github') {
    process.env.NODE_ENV = 'production';
    webpackConfig.output.publicPath = "/webpack-vuejs/dist/"; // 设置输出目录
}
//开发者模式
if (process.env.NODE_ENV === 'development') {
        webpackConfig.nameHash = "";
        webpackConfig.config=path.resolve(__dirname,"./app/js/config.dev.js");
        webpackConfig.output = {//输出文件配置
        path: path.resolve(__dirname, './dist'), // 设置输出目录
        publicPath: "./", //静态文件目录，如果网站路径直接指到dist目录，请注意改为/
        filename: 'js/[name].js', // 输出文件名
        chunkFilename: 'js/[name].js', // 按需加载模块输出文件名
    };
    webpackConfig.plugins.define = new webpack.DefinePlugin({
        "process.env": {//上线后的devtool要配置为source-map,有时候为了性能考虑，一定要配置这个插件
            NODE_ENV: JSON.stringify("development")
        }
    });
    webpackConfig.plugins.clean = function () {
        console.log('开发模式下，无需清理文件夹');
    };//清理文件插件
    webpackConfig.plugins.uglify = function () {
        console.log('开发模式下，无需压缩文件');
    };//压缩文件插件
}
module.exports = {
    //页面入口文件配置
    entry: {
        // vendor: [
        //     'jquery'
        // ],
        main: path.resolve(__dirname, './app/main.js')
    },
    //入口文件输出配置
    output: webpackConfig.output,
    resolve: {
        root: [],
        alias: {
            // jquery: 'jquery',
            // 'zui-css': path.join(nodeModulesPath, '/zui/dist/css/zui.min.css'),
            // 'zui-js': path.join(nodeModulesPath, '/zui/dist/js/zui.min.js'),
            config:webpackConfig.config,//配置文件
            vue: 'vue/dist/vue.js'
        },
        //设置require或import的时候可以不需要带后缀
        extensions: ['','.js', '.less', '.css', '.vue']
    },
    module: {
        //加载器配置
        loaders: [
            {
                test: /\.vue$/,
                loader: "vue"
            },
            {
                test: /\.js$/,
                loader: 'babel',
                exclude: /node_modules/
            },
            {
                test: /\.css$/,
                loader: ExtractTextPlugin.extract('style', 'css')
            }, {
                test: /\.scss/,
                loader: ExtractTextPlugin.extract('style', 'css!sass')
            }, {
                test: /\.(png|jpg|gif)$/,
                loader: 'url-loader',
                query: {
                    limit: 10240, //10kb 图片转base64。设置图片大小，小于此数则转换。
                    name: 'images/[name]' + webpackConfig.nameHash + '.[ext]' //输出目录以及名称
                }
            }, {
                test: /\.(woff|woff2|svg|eot|ttf)\??.*$/,
                loader: 'file',
                query: {
                    name: 'fonts/[name]' + webpackConfig.nameHash + '.[ext]' //输出目录以及名称
                }
            }, {
                test: /\.(htm|html)$/i,
                loader: 'html-withimg-loader?exclude=/upload/'
            }, {
                test: /\.json$/,
                loader: 'json-loader',
                query: {
                    name: 'json/[name]' + webpackConfig.nameHash + '.[ext]' //输出目录以及名称
                }
            },
        ]
    },
    plugins: [
        webpackConfig.plugins.define,//全局变量
        // new webpack.ProvidePlugin({
        //     Config:'config'
        // }),
        webpackConfig.plugins.clean,//清理文件
        // 分离css
        new ExtractTextPlugin('css/[name]' + webpackConfig.nameHash + '.css', {
            allChunks: true
        }),
        new OptimizeCssAssetsPlugin({
			assetNameRegExp: /\.optimize\.css$/g,
			cssProcessor: require('cssnano'),
			cssProcessorOptions: {
				discardComments: {
					removeAll: true
				}
			},
			canPrint: true
		}),
        // new webpack.optimize.CommonsChunkPlugin({
        //         name: "vendor",
        //         filename: 'js/vendor' + webpackConfig.nameHash + '.js',
        //         async: false
        // }),
        webpackConfig.plugins.uglify, //压缩打包的文件
        //允许错误不打断程序
        new webpack.NoErrorsPlugin(),
        new HtmlWebpackPlugin({
            filename: webpackConfig.output.path + '/index.html', //目标文件
            template: __dirname + '/app/index.html', //模板文件
            favicon: __dirname + '/app/images/headIco.png',
            inject: 'body',
            hash: false, //默认为true,代表js、css文件后面会跟一个随机字符串,解决缓存问题
            chunks: ['main'],
            chunksSortMode: 'auto'
        }),
        //把指定文件夹下的文件复制到指定的目录
        new TransferWebpackPlugin([
            {from: 'data', to: 'data'},
            {from: 'images', to: 'images'},
            {from: 'lib', to: 'lib'},
            {from: 'css/fonts', to: 'css/fonts'}
        ], path.resolve(__dirname, './app'))
    ],
    devServer: {//服务器
        historyApiFallback: true,
        progress: true,
        port: 8080,
        headers: {
        },
        proxy: {//接口转发
            '/api': {
                target: 'http://localhost:8081', //转发地址
                changeOrigin: true,
                pathRewrite: {
                    '^/api/(.*)\.json$':'/page/$1'
                }//路由重写，与target组装成新的地址,如“/api/getlogo”转发到“http://localhost/getlogo”
            }
        }
    }
};