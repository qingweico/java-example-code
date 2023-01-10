package oak.newfeature;

/**
 * -------------------- 加密算法 --------------------
 * @author zqw
 * @date 2023/1/1
 * Java 语言安全的基础,主要有两块内容 :
 * 1 : Java 语言的安全设计,比如字节码的校验,内存保护机制等等;
 * 2 : Java 平台的保护机制,比如签名的类库,资源的认证授权等等
 * 而 Java 平台的保护机制,是建立在密码学的基础之上的
 */
 class EncryptionAlgorithm {
    public static void main(String[] args) {

    }
}
/*应该抛弃的算法*/
// MD2
// MD5
// SHA-1
// DES
// 3DES
// RC4
// SSL 3.0
// TLS 1.0
// TLS 1.1
// 密钥小于 1024 位的 RSA 算法
// 密钥小于 1024 位的 DSA 算法
// 密钥小于 1024 位的 Diffie-Hellman 算法
// 密钥小于 256 位的 EC 算法
/*应该退役的算法*/
//密钥大于 1024 位小于 2048 位的 RSA 算法
// 密钥大于 1024 位小于 2048 位的 DSA 算法
// 密钥大于 1024 位小于 2048 位的 Diffie-Hellman 算法
// RSA 签名算法基于 RSA 的密钥交换算法128 位的 AES 算法
/*推荐使用的算法*/
//56 位的 AES 算法
// SHA-256、SHA-512 单向散列函数
// RSASSA-PSS 签名算法
// X25519/X448 密钥交换算法
// EdDSA 签名算法
// JDK 8 不支持 X25519/X488 密钥交换算法,也不支持 EdDSA 签名算法;一个最重要的原因,就是这些算法需要使用新的公开接口